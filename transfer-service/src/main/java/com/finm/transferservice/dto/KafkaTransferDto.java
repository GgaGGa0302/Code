package com.finm.transferservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KafkaTransferDto implements Serializable {
    private Schema schema;
    private Payload payload;
}