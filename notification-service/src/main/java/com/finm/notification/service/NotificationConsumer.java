package com.finm.notification.service;

import com.finm.notification.domain.History;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationConsumer {

    private final HistoryService historyService;
    private final SseEmitterService sseEmitterService;

    @KafkaListener(topics = "transfer-events", groupId = "notification-group")
    public void consumeTransferEvent(Object record) {
        log.info("Received Kafka Event from transfer-events: {}", record);

        try {
            Long accountNumber = 123456789L;
            String transactionType = "TRANSFER";
            Long amount = 10000L;
            Long balanceAfter = 50000L;
            String description = "계좌 이체 알림";

            // 1. DB 저장
            History savedHistory = historyService.saveHistory(accountNumber, transactionType, amount, balanceAfter, description);

            // 2. 실시간 SSE 알림 전송
            sseEmitterService.sendNotification(accountNumber, savedHistory);
            log.info("Successfully saved history and sent SSE notification for account: {}", accountNumber);

        } catch (Exception e) {
            log.error("Failed to process Kafka event: {}", record, e);
        }
    }
}