package com.finm.transfer.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TransferRequest {
    private String fromAccount;
    private String toAccount;
    private Long amount;

    public TransferRequest(String fromAccount, String toAccount, Long amount) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
    }
}