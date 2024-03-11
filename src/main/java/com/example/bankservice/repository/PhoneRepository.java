package com.example.bankservice.repository;

import com.example.bankservice.entity.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Integer>, JpaSpecificationExecutor<Phone> {

    boolean existsPhoneByPhone(String phone);

    Phone findByPhone(String phone);
}
