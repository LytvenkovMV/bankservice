package com.example.bankservice.controller;

import com.example.bankservice.dto.SearchClientResponseDto;
import com.example.bankservice.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Tag(name = "Поиск")
@RestController
@RequestMapping(path = "/bankservice")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @Operation(summary = "Найти клиентов по параметрам")
    @GetMapping(path = {"/search",
            "/search/{date}",
            "/search/{surname}",
            "/search/{name}",
            "/search/{middlename}",
            "/search/{phone}",
            "/search/{email}"})
    public List<SearchClientResponseDto> findClient(@PathVariable(required = false) Optional<Date> date,
                                                               @PathVariable(required = false) Optional<String> surname,
                                                               @PathVariable(required = false) Optional<String> name,
                                                               @PathVariable(required = false) Optional<String> middlename,
                                                               @PathVariable(required = false) Optional<String> phone,
                                                               @PathVariable(required = false) Optional<String> email) {

        if (date.isPresent()) return searchService.findClientsByBirthDate(date.get());
        if (surname.isPresent() && name.isPresent() && middlename.isPresent())
            return searchService.findClientsByFIO(surname.get(), name.get(), middlename.get());
        if (phone.isPresent()) return searchService.findClientsByPhone(phone.get());
        if (email.isPresent()) return searchService.findClientsByEmail(email.get());
        return searchService.findAllClients();
    }
}
