package com.finm.transferservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Payload {
    private String transfer_id;
    private String transaction_type;
    private String from_account;
    private String to_account;
    private Long amount;
    private String status;
    private String requested_at;
    private String completed_at;
}