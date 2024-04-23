package com.example.bankservice.controller;

import com.example.bankservice.dto.SearchClientResponseDto;
import com.example.bankservice.dto.SearchFilterDto;
import com.example.bankservice.service.AuthService;
import com.example.bankservice.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Поиск")
@RestController
@RequestMapping(path = "/bankservice/search")
@RequiredArgsConstructor
public class SearchController {

    private final AuthService authService;
    private final SearchService searchService;

    @Operation(summary = "Приветствие")
    @GetMapping()
    public ResponseEntity<String> greeting(@RequestHeader(HttpHeaders.AUTHORIZATION) String headerAuth) {
        return ResponseEntity.status(HttpStatus.OK).body(searchService.greeting(authService.getUsername(headerAuth)));
    }

    @Operation(summary = "Найти клиентов")
    @PostMapping
    public List<SearchClientResponseDto> findClients(@RequestBody SearchFilterDto searchFilterDto) {
        return searchService.findClients(searchFilterDto);
    }
}
