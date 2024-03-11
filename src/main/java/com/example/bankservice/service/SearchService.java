package com.example.bankservice.service;

import com.example.bankservice.dto.SearchClientResponseDto;
import com.example.bankservice.dto.SearchFilterDto;
import com.example.bankservice.entity.Client;
import com.example.bankservice.entity.Phone;
import com.example.bankservice.mapper.MapStructMapper;
import com.example.bankservice.repository.ClientRepository;
import jakarta.persistence.criteria.Join;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class SearchService {

    private final ClientRepository clientRepository;
    private final MapStructMapper mapStructMapper;

    public SearchService(ClientRepository clientRepository,
                         MapStructMapper mapStructMapper) {
        this.clientRepository = clientRepository;
        this.mapStructMapper = mapStructMapper;
    }

    public String greeting(String username) {
        return "Приветствуем, " + username + "! Это сервис для поиска.";
    }

    public List<SearchClientResponseDto> findClients(SearchFilterDto searchFilterDto) {

        log.info("Получен запрос на поиск клиентов");

        Specification<Client> specification = hasNotNullId();
        if (!searchFilterDto.getSurname().equals("***")) {
            specification = specification.and(hasSurnameLike(searchFilterDto.getSurname()));
        }
        if (!searchFilterDto.getName().equals("***")) {
            specification = specification.and(hasNameLike(searchFilterDto.getName()));
        }
        if (!searchFilterDto.getMiddlename().equals("***")) {
            specification = specification.and(hasMiddlenameLike(searchFilterDto.getMiddlename()));
        }
        if (!searchFilterDto.getBirthDate().equals("***")) {
            specification = specification.and(hasBirthDateGreaterThan(searchFilterDto.getBirthDate()));
        }
        if (!searchFilterDto.getPhone().equals("***")) {
            specification = specification.and(hasPhone(searchFilterDto.getPhone()));
        }
        if (!searchFilterDto.getEmail().equals("***")) {
            specification = specification.and(hasEmail(searchFilterDto.getEmail()));
        }

        List<Client> clients = clientRepository.findAll(specification);
        List<SearchClientResponseDto> dtoList = mapStructMapper.fromClients(clients);

        log.info("Найден список клиентов:" + dtoList);

        return dtoList;
    }

    private static Specification<Client> hasNotNullId() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.isNotNull(root.get("id"));
    }

    private static Specification<Client> hasSurnameLike(String surname) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("surname"), "%" + surname + "%");
    }

    private static Specification<Client> hasNameLike(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }

    private static Specification<Client> hasMiddlenameLike(String middlename) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("middlename"), "%" + middlename + "%");
    }

    private static Specification<Client> hasBirthDateGreaterThan(Date birthDate) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get("birthDate"), birthDate);
    }

    public static Specification<Client> hasPhone(String phone) {
        return (root, query, criteriaBuilder) -> {
            Join<Client, Phone> clientPhoneJoin = root.join("phones");
            return criteriaBuilder.equal(clientPhoneJoin.get("phone"), phone);
        };
    }

    public static Specification<Client> hasEmail(String email) {
        return (root, query, criteriaBuilder) -> {
            Join<Client, Phone> clientPhoneJoin = root.join("emails");
            return criteriaBuilder.equal(clientPhoneJoin.get("email"), email);
        };
    }
}
