package com.example.bankservice.repository;

import com.example.bankservice.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer>, JpaSpecificationExecutor<Client> {

    boolean existsClientByUsername(String username);

    Client findClientByUsername(String username);

    @Query(value = "SELECT charge_balance(:add_percent, :max_percent);", nativeQuery = true)
    void chargeBalance(@Param("add_percent") Integer addPercent, @Param("max_percent") Integer maxPercent);
}
