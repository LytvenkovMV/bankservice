package com.example.bankservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequestDto {
    @JsonProperty(value = "sender-id")
    private Integer senderId;

    @JsonProperty(value = "recipient-id")
    private Integer recipientId;

    @JsonProperty(value = "amount")
    private Double amount;
}
