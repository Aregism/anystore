package com.anystore.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Authority {

    public static final Authority superadminAuthority = new Authority();
    public static final Authority adminAuthority = new Authority();
    public static final Authority userAuthority = new Authority();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column(name = "authority", unique = true, nullable = false)
    private String role;

    @Override
    public String toString() {
        return "Authority{" +
                "id=" + id +
                ", role='" + role + '\'' +
                '}';
    }
}
