package com.example.bankservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "DTO для перевода")
public class TransferRequestDto {
    @ApiModelProperty(value = "ИД отправителя", required = true)
    @JsonProperty(value = "sender-id")
    private Integer senderId;

    @ApiModelProperty(value = "ИД получателя", required = true)
    @JsonProperty(value = "recipient-id")
    private Integer recipientId;

    @ApiModelProperty(value = "Сумма перевода", required = true)
    @JsonProperty(value = "amount")
    private Double amount;
}
