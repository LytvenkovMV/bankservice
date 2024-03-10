package com.example.bankservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchClientResponseDto {
    private String surname;
    private String name;
    private String middlename;
    private Date birthDate;
    private String[] phones;
    private String[] emails;
}
