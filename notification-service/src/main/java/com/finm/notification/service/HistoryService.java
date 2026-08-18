package com.finm.notification.service;

import com.finm.notification.domain.History;
import com.finm.notification.dto.NotificationResponseDto;
import com.finm.notification.repository.HistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HistoryService {

    private final HistoryRepository historyRepository;

    // 히스토리 저장
    @Transactional
    public History saveHistory(Long accountNumber, String transactionType, Long amount, Long balanceAfter, String description) {
        History history = History.builder()
                .accountNumber(accountNumber)
                .transactionType(transactionType)
                .amount(amount)
                .balanceAfter(balanceAfter)
                .description(description)
                .isRead(false)
                .build();
        return historyRepository.save(history);
    }

    // 계좌별 알림 목록 조회
    public List<NotificationResponseDto> getHistoryByAccount(Long accountNumber) {
        return historyRepository.findByAccountNumberOrderByCreatedAtDesc(accountNumber)
                .stream()
                .map(NotificationResponseDto::from)
                .collect(Collectors.toList());
    }

    // 알림 읽음 상태 변경 (추가할 메서드)
    @Transactional
    public void markAsRead(Long historyId) {
        History history = historyRepository.findById(historyId)
                .orElseThrow(() -> new IllegalArgumentException("해당 알림 내역이 존재하지 않습니다. id=" + historyId));
        history.read(); // History 엔티티 내에 isRead = true 처리 메서드 필요
    }
}