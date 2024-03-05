package com.example.bankservice.dto;

import com.example.bankservice.entity.Email;
import com.example.bankservice.entity.Phone;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "DTO для добавления клиента")
public class AddClientRequestDto {
    @ApiModelProperty(value = "Фамилия", required = true)
    private String surname;

    @ApiModelProperty(value = "Имя", required = true)
    private String name;

    @ApiModelProperty(value = "Отчество", required = true)
    private String middlename;

    @ApiModelProperty(value = "Дата рождения", required = true)
    private LocalDate birthDate;

    @ApiModelProperty(value = "Баланс счета", required = true)
    private Double balance;

    @ApiModelProperty(value = "Список телефонов", required = true)
    private List<String> phones;

    @ApiModelProperty(value = "Список емайлов", required = true)
    private List<String> emails;
}
