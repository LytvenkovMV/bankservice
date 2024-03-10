package com.example.bankservice.repository;

import com.example.bankservice.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    boolean existsClientByUsername(String username);

    boolean existsClientById(Integer id);

    Client findClientByUsername(String username);
    List<Client> findClientsByBirthDateIsAfter(Date date);
    List<Client> findClientsBySurnameLikeAndNameLikeAndMiddlenameLike(String surname, String name, String middlename);
}
