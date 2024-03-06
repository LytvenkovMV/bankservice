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
@ApiModel(value = "DTO для изменения телефона клиента")
public class ModifyPhoneRequestDto {
    @ApiModelProperty(value = "ИД клиента", required = true)
    @JsonProperty(value = "client-id")
    private Integer clientId;

    @ApiModelProperty(value = "Номер телефона", required = true)
    @JsonProperty(value = "phone")
    private String phone;
}
