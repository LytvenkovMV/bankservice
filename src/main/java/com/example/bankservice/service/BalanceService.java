package com.example.bankservice.service;

import com.example.bankservice.entity.Client;
import com.example.bankservice.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class BalanceService {

    private final double maxCoeff;
    private final double addCoeff;
    private static final String delay = "${balance-service.period-millis}";
    private final ClientRepository clientRepository;

    public BalanceService(@Value("${balance-service.maximum-percent}") Integer maxPercent,
                          @Value("${balance-service.additional-percent}") Integer addPercent,
                          ClientRepository clientRepository) {
        this.maxCoeff = ((double) maxPercent) / 100;
        this.addCoeff = ((double) addPercent) / 100;
        this.clientRepository = clientRepository;
    }

    @Transactional
    @Scheduled(initialDelayString = delay, fixedRateString = delay)
    public void increaseBalance() {

        List<Client> clients = clientRepository.findAll();
        for (Client client : clients) {
            client.setCurrBalance(getNewBalance(client.getInitBalance(), client.getCurrBalance()));
        }
        clientRepository.saveAll(clients);

        log.info("Выполнено периодическое начисление процентов");
    }

    private Double getNewBalance(Double initBalance, Double currBalance) {
        double newBalance = currBalance + currBalance * addCoeff;
        double maxBalance = initBalance * maxCoeff;
        if (newBalance > maxBalance) newBalance = maxBalance;
        return newBalance;
    }
}
