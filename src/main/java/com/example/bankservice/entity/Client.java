package com.example.bankservice.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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

    @Column(name = "login")
    private String login;

    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "init_balance")
    private Double initBalance;

    @Column(name = "curr_balance")
    private Double currBalance;

    @OneToMany(mappedBy = "client")
    private List<Phone> phones;

    @OneToMany(mappedBy = "client")
    private List<Email> emails;
}
