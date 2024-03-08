package com.example.househubadmin.controller;

import com.example.househubadmin.dto.user.notary.NotaryDtoForAdd;
import com.example.househubadmin.dto.user.UserDtoForViewAll;
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
import org.springframework.web.multipart.MultipartFile;

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
    @GetMapping("/get-all")
    public ResponseEntity<Page<UserDtoForViewAll>> getAll(@RequestParam Integer page, @RequestParam Integer pageSize){
        return ResponseEntity.ok(notaryService.getAll(page, pageSize));
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authorized"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
    })
    @Operation(summary = "The request to add or update(if id isn't null) a notary")
    @PostMapping(value = "/add")
    public ResponseEntity<Map<String, String>> add(@ModelAttribute @Valid NotaryDtoForAdd notaryDtoForAdd, BindingResult bindingResult) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
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
    @DeleteMapping("/delete/{notaryId}")
    public ResponseEntity<String> deleteById(@PathVariable Long notaryId){
        notaryService.deleteById(notaryId);
        return ResponseEntity.ok("deleted");
    }
}