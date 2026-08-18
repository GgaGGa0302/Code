package com.finm.transferservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.finm.transferservice.domain.Transfer;
import com.finm.transferservice.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransferProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    // Kafka Sink Connector용 Schema 정의
    private final List<Field> fields = Arrays.asList(
            new Field("string", false, "transfer_id"),
            new Field("string", false, "transaction_type"),
            new Field("string", true, "from_account"),
            new Field("string", true, "to_account"),
            new Field("int64", false, "amount"),
            new Field("string", false, "status"),
            new Field("string", true, "requested_at"),
            new Field("string", true, "completed_at")
    );

    private final Schema schema = Schema.builder()
            .type("struct")
            .fields(fields)
            .optional(false)
            .name("transfers")
            .build();

    // 일반 JSON 이벤트 전송 (MSA 서비스 간 비동기 알림/이벤트 처리용)
    public TransferResponse send(String topic, TransferResponse responseDto) {
        try {
            String jsonInString = objectMapper.writeValueAsString(responseDto);
            kafkaTemplate.send(topic, responseDto.getTransferId(), jsonInString);
            log.info("[Kafka Producer] Sent event to topic '{}': transferId={}", topic, responseDto.getTransferId());
        } catch (JsonProcessingException e) {
            log.error("[Kafka Producer] JSON parsing error: ", e);
        }
        return responseDto;
    }

    // Kafka Sink Connector용 전송 (Schema + Payload 구조)
    public void send4SinkConnect(String topic, Transfer transfer) {
        Payload payload = Payload.builder()
                .transfer_id(transfer.getTransferId())
                .transaction_type(transfer.getTransactionType().name())
                .from_account(transfer.getFromAccount())
                .to_account(transfer.getToAccount())
                .amount(transfer.getAmount())
                .status(transfer.getStatus().name())
                .requested_at(transfer.getRequestedAt() != null ? transfer.getRequestedAt().toString() : null)
                .completed_at(transfer.getCompletedAt() != null ? transfer.getCompletedAt().toString() : null)
                .build();

        KafkaTransferDto kafkaTransferDto = new KafkaTransferDto(schema, payload);

        try {
            String jsonInString = objectMapper.writeValueAsString(kafkaTransferDto);
            kafkaTemplate.send(topic, transfer.getTransferId(), jsonInString);
            log.info("[Kafka SinkConnect Producer] Sent data to topic '{}': transferId={}", topic, transfer.getTransferId());
        } catch (JsonProcessingException e) {
            log.error("[Kafka SinkConnect Producer] JSON parsing error: ", e);
        }
    }
}