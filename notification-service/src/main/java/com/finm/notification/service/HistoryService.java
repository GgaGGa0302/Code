package com.finm.notification.service;

import com.finm.notification.domain.History;
import com.finm.notification.dto.NotificationResponseDto;
import com.finm.notification.repository.HistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    // 계좌별 거래 내역 DTO 목록 조회
    @Transactional(readOnly = true)
    public List<NotificationResponseDto> getHistoryByAccount(Long accountNumber) {
        return historyRepository.findByAccountNumberOrderByCreatedAtDesc(accountNumber)
                .stream()
                .map(NotificationResponseDto::from)
                .collect(Collectors.toList());
    }

    // 특정 알림 ID 읽음 상태 변경
    @Transactional
    public void markAsRead(Long historyId) {
        History history = historyRepository.findById(historyId)
                .orElseThrow(() -> new IllegalArgumentException("해당 알림이 존재하지 않습니다. id=" + historyId));
        history.markAsRead();
    }
}