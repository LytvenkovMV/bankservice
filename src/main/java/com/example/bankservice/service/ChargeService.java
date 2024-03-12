package com.example.bankservice.service;

import com.example.bankservice.entity.Client;
import com.example.bankservice.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class ChargeService {

    private final double maxCoeff;
    private final double chargeCoeff;
    private static final String delay = "${balance-service.period-millis}";
    private final ClientRepository clientRepository;

    public ChargeService(@Value("${balance-service.maximum-percent}") Integer maxPercent,
                         @Value("${balance-service.additional-percent}") Integer addPercent,
                         ClientRepository clientRepository) {
        this.maxCoeff = ((double) maxPercent) / 100;
        this.chargeCoeff = ((double) addPercent) / 100;
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

    private BigDecimal getNewBalance(BigDecimal initBalance, BigDecimal currBalance) {
        BigDecimal newBalance = currBalance.add(currBalance.multiply(new BigDecimal(chargeCoeff)));
        BigDecimal maxBalance = initBalance.multiply(new BigDecimal(maxCoeff));
        if (newBalance.compareTo(maxBalance) > 0) {
            newBalance = maxBalance;
        }
        return newBalance;
    }
}
