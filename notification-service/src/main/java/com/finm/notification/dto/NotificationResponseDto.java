package com.finm.notification.dto;

import com.finm.notification.domain.History;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponseDto {

    private Long id;
    private Long accountNumber;
    private String transactionType;
    private Long amount;
    private Long balanceAfter;
    private String description;
    private boolean isRead; // 추가
    private LocalDateTime createdAt;

    public static NotificationResponseDto from(History history) {
        return NotificationResponseDto.builder()
                .id(history.getId())
                .accountNumber(history.getAccountNumber())
                .transactionType(history.getTransactionType())
                .amount(history.getAmount())
                .balanceAfter(history.getBalanceAfter())
                .description(history.getDescription())
                .isRead(history.isRead()) // 추가
                .createdAt(history.getCreatedAt())
                .build();
    }
}