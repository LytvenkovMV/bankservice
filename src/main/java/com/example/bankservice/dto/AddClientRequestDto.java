package com.example.bankservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddClientRequestDto {
    @JsonProperty(value = "surname")
    private String surname;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "middlename")
    private String middlename;

    @JsonProperty(value = "username")
    private String username;

    @JsonProperty(value = "password")
    private String password;

    @JsonProperty(value = "birth_date")
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date birthDate;

    @JsonProperty(value = "balance")
    private String balance;

    @JsonProperty(value = "phones")
    private String[] phones;

    @JsonProperty(value = "emails")
    private String[] emails;
}
