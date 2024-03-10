package com.example.bankservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequestDto {
    @JsonProperty(value = "recipient-username")
    private String recipientUsername;

    @JsonProperty(value = "amount")
    private Double amount;
}
