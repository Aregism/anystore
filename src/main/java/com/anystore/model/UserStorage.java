package com.anystore.model;

import com.anystore.model.enums.DeleteType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Table
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserStorage {

    @Id
    @Column
    private int id;

    @Column(name = "deleted_account_email")
    private String email;

    @Column(name = "deleted_date")
    private LocalDate dateDeleted;

    @Column(name = "deleted_time")
    private LocalTime timeDeleted;

    @Column(name = "delete_type")
    @Enumerated(EnumType.STRING)
    private DeleteType deleteType;
}
