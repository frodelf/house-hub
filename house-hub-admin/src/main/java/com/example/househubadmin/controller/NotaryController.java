package com.example.househubadmin.controller;

import com.example.househubadmin.dto.notary.NotaryDtoForAdd;
import com.example.househubadmin.dto.notary.NotaryDtoForViewAll;
import com.example.househubadmin.service.NotaryService;
import com.example.househubadmin.validator.NotaryValidator;
import io.minio.errors.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/notary")
@RequiredArgsConstructor
@Tag(name = "Notary controller", description = "Notary API")
public class NotaryController {
    private final NotaryService notaryService;
    private final NotaryValidator notaryValidator;
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authorized"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
    })
    @Operation(summary = "The request to get all notaries")
    @GetMapping("/get-all-notaries")
    public ResponseEntity<Page<NotaryDtoForViewAll>> getAllNotaries(@RequestParam Integer page, @RequestParam Integer pageSize){
        return ResponseEntity.ok(notaryService.getAll(page, pageSize));
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authorized"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
    })
    @Operation(summary = "The request to add or update(if id isn't null) a notary")
    @GetMapping("/add-notary")
    public ResponseEntity<Map<String, String>> getAllNotaries(@RequestBody @Valid NotaryDtoForAdd notaryDtoForAdd, BindingResult bindingResult) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        notaryValidator.validate(notaryDtoForAdd, bindingResult);
        Map<String, String> errorsMap = new HashMap<>();
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(error -> errorsMap.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorsMap);
        }
        notaryService.add(notaryDtoForAdd);
        return ResponseEntity.ok(Collections.singletonMap("status", "saved"));
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authorized"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
    })
    @Operation(summary = "The request to delete the notary by id")
    @GetMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id){
        notaryService.deleteById(id);
        return ResponseEntity.ok("deleted");
    }
}