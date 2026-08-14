package com.finm.transfer.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.finm.transfer.domain.TransactionType;
import com.finm.transfer.domain.Transfer;
import com.finm.transfer.domain.TransferStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.ALWAYS)
public class TransferResponse {
    private String transferId;
    private TransactionType transactionType;
    private String fromAccount;
    private String toAccount;
    private Long amount;
    private TransferStatus status;
    private LocalDateTime requestedAt;
    private LocalDateTime completedAt;

    public static TransferResponse from(Transfer transfer) {
        return TransferResponse.builder()
                .transferId(transfer.getTransferId())
                .transactionType(transfer.getTransactionType())
                .fromAccount(transfer.getFromAccount())
                .toAccount(transfer.getToAccount())
                .amount(transfer.getAmount())
                .status(transfer.getStatus())
                .requestedAt(transfer.getRequestedAt())
                .completedAt(transfer.getCompletedAt())
                .build();
    }
}