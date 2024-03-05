package com.example.bankservice.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "surname")
    private String surname;

    @Column(name = "name")
    private String name;

    @Column(name = "middlename")
    private String middlename;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "balance")
    private Double balance;

    @OneToMany(mappedBy = "phone")
    private List<Phone> phones;

    @OneToMany(mappedBy = "email")
    private List<Email> emails;
}
