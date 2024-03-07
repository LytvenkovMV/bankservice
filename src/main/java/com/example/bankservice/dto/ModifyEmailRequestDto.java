package com.example.bankservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "DTO для изменения электронной почты клиента")
public class ModifyEmailRequestDto {
    @ApiModelProperty(value = "ИД клиента", required = true)
    @JsonProperty(value = "client-id")
    private Integer clientId;

    @ApiModelProperty(value = "Адрес электронной почты", required = true)
    @JsonProperty(value = "email")
    private String email;
}
