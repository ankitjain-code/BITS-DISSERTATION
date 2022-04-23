# Microservice Authentication and Authorization 
```
Bits Dissertation Project
```


## Hibernate ddl auto (create, create-drop, validate, update)
```
spring.jpa.hibernate.ddl-auto= update
```


## App Properties
```
spring.datasource.url= jdbc:mysql://localhost:3307/bits?useSSL=false
spring.datasource.username= root
spring.datasource.password= root

spring.jpa.properties.hibernate.dialect= org.hibernate.dialect.MySQL5InnoDBDialect
spring.jpa.hibernate.ddl-auto= update
```

## App Properties
```
bits.app.jwtSecret= ankitSecretKey
bits.app.jwtExpirationMs= 86400000
```


## Run Spring Boot application
```
mvn spring-boot:run
```

## Run following SQL insert statements
```
INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_MODERATOR');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');
```
