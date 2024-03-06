package com.example.bankservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "DTO для добавления клиента")
public class AddClientRequestDto {
    @ApiModelProperty(value = "Фамилия", required = true)
    @JsonProperty(value = "surname")
    private String surname;

    @ApiModelProperty(value = "Имя", required = true)
    @JsonProperty(value = "name")
    private String name;

    @ApiModelProperty(value = "Отчество", required = true)
    @JsonProperty(value = "middlename")
    private String middlename;

    @ApiModelProperty(value = "Логин", required = true)
    @JsonProperty(value = "login")
    private String login;

    @ApiModelProperty(value = "Дата рождения", required = true)
    @JsonProperty(value = "birth_date")
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date birthDate;

    @ApiModelProperty(value = "Баланс счета", required = true)
    @JsonProperty(value = "balance")
    private Double balance;

    @ApiModelProperty(value = "Список телефонов", required = true)
    @JsonProperty(value = "phones")
    private String[] phones;

    @ApiModelProperty(value = "Список емайлов", required = true)
    @JsonProperty(value = "emails")
    private String[] emails;
}
