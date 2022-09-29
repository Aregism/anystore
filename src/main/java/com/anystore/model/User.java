package com.anystore.model;

import com.anystore.model.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Table
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    public static final User superadminUser = new User();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "lastname", nullable = false)
    private String lastName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "dateofbirth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "authorities")
    @OneToMany
    private Set<Authority> authorities;

    @Column(name = "dateregistered")
    private LocalDate dateRegistered = LocalDate.now();

    @Column(name = "timeregistered")
    private LocalTime timeRegistered = LocalTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private UserStatus userStatus = UserStatus.UNVERIFIED;

    @Column(name = "activationcode")
    private String activationCode;

    @Column(name = "accountactvateddate")
    private LocalDate accountActivatedDate;

    @Column(name = "accountactvatedtime")
    private LocalTime accountActivatedTime;

    @Column(name = "codesentdate")
    private LocalDate codeSentDate;

    @Column(name = "codesenttime")
    private LocalTime codeSentTime;

    @Column(name = "token")
    private String token;

    @Column(name = "tokensentdate")
    private LocalDate tokenSentDate;

    @Column(name = "tokensenttime")
    private LocalTime tokenSentTime;
}
