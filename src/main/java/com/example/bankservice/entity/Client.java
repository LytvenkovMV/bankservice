package com.example.bankservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
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

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "init_balance")
    private BigDecimal initBalance;

    @Column(name = "curr_balance")
    private BigDecimal currBalance;

    @OneToMany(mappedBy = "client")
    private List<Phone> phones;

    @OneToMany(mappedBy = "client")
    private List<Email> emails;
}
