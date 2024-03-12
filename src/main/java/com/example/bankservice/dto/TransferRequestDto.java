package com.example.bankservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequestDto {
    @JsonProperty(value = "recipient-username")
    private String recipientUsername;

    @JsonProperty(value = "amount")
    private String amount;
}
