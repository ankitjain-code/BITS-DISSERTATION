package com.bits.springjwt.models;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class BitsRole {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Enumerated(EnumType.STRING)
  @Column(length = 20)
  private BitsEnumRole name;

  public BitsRole() {

  }

  public BitsRole(BitsEnumRole name) {
    this.name = name;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public BitsEnumRole getName() {
    return name;
  }

  public void setName(BitsEnumRole name) {
    this.name = name;
  }
}