package com.finm.notification.service;

import com.finm.notification.domain.History;
import com.finm.notification.repository.HistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HistoryService {

    private final HistoryRepository historyRepository;

    // 거래 내역 DB 저장
    @Transactional
    public History saveHistory(Long accountNumber, String transactionType, Long amount, Long balanceAfter, String description) {
        History history = History.builder()
                .accountNumber(accountNumber)
                .transactionType(transactionType)
                .amount(amount)
                .balanceAfter(balanceAfter)
                .description(description)
                .build();

        log.info("Saving history record for account: {}", accountNumber);
        return historyRepository.save(history);
    }

    // 계좌별 거래 내역 조회
    @Transactional(readOnly = true)
    public List<History> getHistoryByAccount(Long accountNumber) {
        return historyRepository.findByAccountNumberOrderByCreatedAtDesc(accountNumber);
    }
}