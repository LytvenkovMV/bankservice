package com.example.bankservice.service;

import com.example.bankservice.comparator.ComparatorByBirthDate;
import com.example.bankservice.comparator.ComparatorById;
import com.example.bankservice.comparator.ComparatorBySurname;
import com.example.bankservice.dto.SearchClientResponseDto;
import com.example.bankservice.entity.Client;
import com.example.bankservice.entity.Email;
import com.example.bankservice.entity.Phone;
import com.example.bankservice.mapper.MapStructMapper;
import com.example.bankservice.repository.ClientRepository;
import com.example.bankservice.repository.EmailRepository;
import com.example.bankservice.repository.PhoneRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class SearchService {

    private final ClientRepository clientRepository;
    private final PhoneRepository phoneRepository;
    private final EmailRepository emailRepository;
    private final MapStructMapper mapStructMapper;

    public SearchService(ClientRepository clientRepository,
                         PhoneRepository phoneRepository,
                         EmailRepository emailRepository,
                         MapStructMapper mapStructMapper) {
        this.clientRepository = clientRepository;
        this.phoneRepository = phoneRepository;
        this.emailRepository = emailRepository;
        this.mapStructMapper = mapStructMapper;
    }

    public List<SearchClientResponseDto> findAllClients() {

        log.info("Получен запрос на поиск всех клиентов");

        List<Client> clients = clientRepository.findAll();
        clients.sort(new ComparatorById());
        List<SearchClientResponseDto> dtoList = new ArrayList<>();
        for (Client c : clients) {
            dtoList.add(mapStructMapper.fromClient(c));
        }
        log.info("Найден список клиентов:" + dtoList);

        return dtoList;
    }

    public List<SearchClientResponseDto> findClientsByBirthDate(Date sample) {

        log.info("Получен запрос на поиск клиентов по дате рождения:" + sample);

        List<Client> clients = clientRepository.findClientsByBirthDateIsAfter(sample);
        clients.sort(new ComparatorByBirthDate());
        List<SearchClientResponseDto> dtoList = new ArrayList<>();
        for (Client c : clients) {
            dtoList.add(mapStructMapper.fromClient(c));
        }

        log.info("Найден список клиентов:" + dtoList);

        return dtoList;
    }

    public List<SearchClientResponseDto> findClientsByFIO(String surname, String name, String middlename) {

        log.info("Получен запрос на поиск клиентов по ФИО:" + surname + name + middlename);

        List<Client> clients = clientRepository.findClientsBySurnameLikeAndNameLikeAndMiddlenameLike(surname, name, middlename);
        clients.sort(new ComparatorBySurname());
        List<SearchClientResponseDto> dtoList = new ArrayList<>();
        for (Client c : clients) {
            dtoList.add(mapStructMapper.fromClient(c));
        }

        log.info("Найден список клиентов:" + dtoList);

        return dtoList;
    }

    public List<SearchClientResponseDto> findClientsByPhone(String sample) {

        log.info("Получен запрос на поиск клиентов по номеру телефона:" + sample);

        List<Client> clients = new ArrayList<>();
        if (phoneRepository.existsPhoneByPhone(sample)) {
            Phone phone = phoneRepository.findByPhone(sample);
            clients.add(phone.getClient());
        }
        List<SearchClientResponseDto> dtoList = new ArrayList<>();
        for (Client c : clients) {
            dtoList.add(mapStructMapper.fromClient(c));
        }

        log.info("Найден клиент:" + dtoList);

        return dtoList;
    }

    public List<SearchClientResponseDto> findClientsByEmail(String sample) {

        log.info("Получен запрос на поиск клиентов по email:" + sample);

        List<Client> clients = new ArrayList<>();
        if (emailRepository.existsEmailByEmail(sample)) {
            Email email = emailRepository.findByEmail(sample);
            clients.add(email.getClient());
        }
        List<SearchClientResponseDto> dtoList = new ArrayList<>();
        for (Client c : clients) {
            dtoList.add(mapStructMapper.fromClient(c));
        }

        log.info("Найден клиент:" + dtoList);

        return dtoList;
    }
}
