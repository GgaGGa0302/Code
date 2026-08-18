package com.finm.notification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationConsumer {

    private final HistoryService historyService;

    // 거래/이체 이벤트 발행 시 메시지 수신 및 DB 저장
    @KafkaListener(topics = "transfer-events", groupId = "notification-group")
    public void consumeTransferEvent(Object record) {
        log.info("Received Kafka Event from transfer-events: {}", record);

        try {
            // TODO: 추후 Avro Schema 객체(TransferEvent) 확정 시 record 객체 필드값 직접 전달
            Long accountNumber = 123456789L;
            String transactionType = "TRANSFER";
            Long amount = 10000L;
            Long balanceAfter = 50000L;
            String description = "계좌 이체 알림";

            historyService.saveHistory(accountNumber, transactionType, amount, balanceAfter, description);
            log.info("Successfully saved notification history for account: {}", accountNumber);

        } catch (Exception e) {
            log.error("Failed to process Kafka event and save history: {}", record, e);
        }
    }
}