package com.example.bankservice.service;

import com.example.bankservice.comparator.ComparatorByBirthDate;
import com.example.bankservice.comparator.ComparatorById;
import com.example.bankservice.comparator.ComparatorBySurname;
import com.example.bankservice.entity.Client;
import com.example.bankservice.entity.Email;
import com.example.bankservice.entity.Phone;
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

    public SearchService(ClientRepository clientRepository,
                         PhoneRepository phoneRepository,
                         EmailRepository emailRepository) {
        this.clientRepository = clientRepository;
        this.phoneRepository = phoneRepository;
        this.emailRepository = emailRepository;
    }

    public List<Client> findAllClients() {

        log.info("Получен запрос на поиск всех клиентов");

        List<Client> clients = clientRepository.findAll();
        clients.sort(new ComparatorById());

        log.info("Найден список клиентов:" + clients);

        return clients;
    }

    public List<Client> findClientsByBirthDate(Date sample) {

        log.info("Получен запрос на поиск клиентов по дате рождения:" + sample);

        List<Client> clients = clientRepository.findClientsByBirthDateIsAfter(sample);
        clients.sort(new ComparatorByBirthDate());

        log.info("Найден список клиентов:" + clients);

        return clients;
    }

    public List<Client> findClientsByFIO(String surname, String name, String middlename) {

        log.info("Получен запрос на поиск клиентов по ФИО:" + surname + name + middlename);

        List<Client> clients = clientRepository.findClientsBySurnameLikeAndNameLikeAndMiddlenameLike(surname, name, middlename);
        clients.sort(new ComparatorBySurname());

        log.info("Найден список клиентов:" + clients);

        return null;
    }

    public List<Client> findClientsByPhone(String sample) {

        log.info("Получен запрос на поиск клиентов по номеру телефона:" + sample);

        List<Client> clients = new ArrayList<>();
        if (phoneRepository.existsPhoneByPhone(sample)) {
            Phone phone = phoneRepository.findByPhone(sample);
            clients.add(phone.getClient());
        }

        log.info("Найден клиент:" + clients);

        return clients;
    }

    public List<Client> findClientsByEmail(String sample) {

        log.info("Получен запрос на поиск клиентов по email:" + sample);

        List<Client> clients = new ArrayList<>();
        if (emailRepository.existsEmailByEmail(sample)) {
            Email email = emailRepository.findByEmail(sample);
            clients.add(email.getClient());
        }

        log.info("Найден клиент:" + clients);

        return clients;
    }
}
