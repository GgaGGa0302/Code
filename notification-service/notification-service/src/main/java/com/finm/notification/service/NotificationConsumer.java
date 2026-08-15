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

    // 거래/이체 이벤트 발행 시 메시지 수신
    @KafkaListener(topics = "transfer-events", groupId = "notification-group")
    public void consumeTransferEvent(Object record) {
        log.info("Received Kafka Event from transfer-events: {}", record);

        // TODO: 팀 공통 Avro Schema 정의 후 DTO 매핑 및 History DB 저장 연동 진행 예정
        // historyService.saveHistory(...);
    }
}