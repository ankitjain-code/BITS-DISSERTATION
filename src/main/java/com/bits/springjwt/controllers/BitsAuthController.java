package com.bits.springjwt.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bits.springjwt.models.BitsEnumRole;
import com.bits.springjwt.models.BitsRole;
import com.bits.springjwt.models.BitsUser;
import com.bits.springjwt.payload.request.BitsLoginRequest;
import com.bits.springjwt.payload.request.BitsSignupRequest;
import com.bits.springjwt.payload.response.BitsJwtResponse;
import com.bits.springjwt.payload.response.BitsMessageResponse;
import com.bits.springjwt.repository.BitsRoleRepository;
import com.bits.springjwt.repository.BitsUserRepository;
import com.bits.springjwt.security.jwt.BitsJwtUtils;
import com.bits.springjwt.security.services.BitsUserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class BitsAuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  BitsUserRepository userRepository;

  @Autowired
  BitsRoleRepository roleRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  BitsJwtUtils jwtUtils;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody BitsLoginRequest loginRequest) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);
    
    BitsUserDetailsImpl userDetails = (BitsUserDetailsImpl) authentication.getPrincipal();    
    List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());

    return ResponseEntity.ok(new BitsJwtResponse(jwt, 
                         userDetails.getId(), 
                         userDetails.getUsername(), 
                         userDetails.getEmail(), 
                         roles));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody BitsSignupRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity
          .badRequest()
          .body(new BitsMessageResponse("Error: Username is already taken!"));
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity
          .badRequest()
          .body(new BitsMessageResponse("Error: Email is already in use!"));
    }

    // Create new user's account
    BitsUser user = new BitsUser(signUpRequest.getUsername(), 
               signUpRequest.getEmail(),
               encoder.encode(signUpRequest.getPassword()));

    Set<String> strRoles = signUpRequest.getRole();
    Set<BitsRole> roles = new HashSet<>();

    if (strRoles == null) {
      BitsRole userRole = roleRepository.findByName(BitsEnumRole.ROLE_USER)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
        case "admin":
          BitsRole adminRole = roleRepository.findByName(BitsEnumRole.ROLE_ADMIN)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(adminRole);

          break;
        case "mod":
          BitsRole modRole = roleRepository.findByName(BitsEnumRole.ROLE_MODERATOR)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(modRole);

          break;
        default:
          BitsRole userRole = roleRepository.findByName(BitsEnumRole.ROLE_USER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(userRole);
        }
      });
    }

    user.setRoles(roles);
    userRepository.save(user);

    return ResponseEntity.ok(new BitsMessageResponse("User registered successfully!"));
  }
}
