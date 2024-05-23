package com.example.bankservice.service;

import com.example.bankservice.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void chargeBalance() {

        Integer maxPercent = Integer.parseInt(maxPercentProp);
        Integer addPercent = Integer.parseInt(addPercentProp);
        clientRepository.chargeBalance(addPercent, maxPercent);

        log.info("Выполнено периодическое начисление процентов");
    }
}
