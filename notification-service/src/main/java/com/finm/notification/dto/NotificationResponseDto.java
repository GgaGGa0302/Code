package com.finm.notification.dto;

import com.finm.notification.domain.History;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class NotificationResponseDto {
    private Long id;
    private Long accountNumber;
    private String transactionType;
    private Long amount;
    private Long balanceAfter;
    private String description;
    private LocalDateTime createdAt;

    public static NotificationResponseDto from(History entity) {
        return NotificationResponseDto.builder()
                .id(entity.getId())
                .accountNumber(entity.getAccountNumber())
                .transactionType(entity.getTransactionType())
                .amount(entity.getAmount())
                .balanceAfter(entity.getBalanceAfter())
                .description(entity.getDescription())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}