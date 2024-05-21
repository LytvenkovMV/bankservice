package com.example.bankservice.service;

import com.example.bankservice.entity.Client;
import com.example.bankservice.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChargeService {

    private static final String delay = "${balance-service.period-millis}";
    @Value("${balance-service.maximum-percent}")
    private String maxPercentProp;
    @Value("${balance-service.additional-percent}")
    private String addPercentProp;
    private final ClientRepository clientRepository;

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

        double maxPercent = Double.parseDouble(maxPercentProp);
        double addPercent = Double.parseDouble(addPercentProp);

        BigDecimal maxCoeff = new BigDecimal(maxPercent / 100);
        BigDecimal chargeCoeff = new BigDecimal(addPercent / 100);

        BigDecimal newBalance = currBalance.add(currBalance.multiply(chargeCoeff));
        BigDecimal maxBalance = initBalance.multiply(maxCoeff);

        if (newBalance.compareTo(maxBalance) > 0) {
            newBalance = maxBalance;
        }
        return newBalance;
    }
}
