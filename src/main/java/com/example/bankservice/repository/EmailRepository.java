package com.example.bankservice.repository;

import com.example.bankservice.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends JpaRepository<Email, Integer>, JpaSpecificationExecutor<Email> {

    boolean existsEmailByEmail(String email);

    Email findByEmail(String email);
}
