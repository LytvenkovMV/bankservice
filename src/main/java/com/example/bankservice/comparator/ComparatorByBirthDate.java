package com.example.bankservice.comparator;

import com.example.bankservice.entity.Client;

import java.util.Comparator;

public class ComparatorByBirthDate implements Comparator<Client> {
    @Override
    public int compare(Client o1, Client o2) {

        return o1.getBirthDate().compareTo(o2.getBirthDate());
    }
}
