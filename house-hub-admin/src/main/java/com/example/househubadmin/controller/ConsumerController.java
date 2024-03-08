package com.example.househubadmin.controller;

import com.example.househubadmin.dto.user.UserDtoForViewAll;
import com.example.househubadmin.entity.enums.StatusUser;
import com.example.househubadmin.service.ConsumerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/api/v1/consumer")
@RequiredArgsConstructor
@Tag(name = "Consumer controller", description = "Consumer API")
public class ConsumerController {
    private final ConsumerService consumerService;
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authorized"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @Operation(summary = "The request to get all consumers")
    @GetMapping("/get-all")
    public ResponseEntity<Page<UserDtoForViewAll>> getAll(@Parameter(description = "Page for pagination") @RequestParam Integer page, @Parameter(description = "Page size for page numbering") @RequestParam Integer pageSize, @Parameter(description = "Consumer's name for filtering") @RequestParam String consumerName){
        return ResponseEntity.ok(consumerService.getAll(page, pageSize, consumerName, StatusUser.ACTIVE));
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authorized"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @Operation(summary = "The request to get all blocked consumers")
    @GetMapping("/get-all-blocked")
    public ResponseEntity<Page<UserDtoForViewAll>> getAllBlocked(@Parameter(description = "Page for pagination") @RequestParam Integer page, @Parameter(description = "Page size for page numbering") @RequestParam Integer pageSize, @Parameter(description = "Consumer's name for filtering") @RequestParam String consumerName){
        return ResponseEntity.ok(consumerService.getAll(page, pageSize, consumerName, StatusUser.REMOVE));
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authorized"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @Operation(summary = "The request to change consumer's status by id")
    @PutMapping("/change-status/{consumerId}")
    public ResponseEntity<String> changeStatusForFlat(@Parameter(description = "Consumer id whose status will be changed") @PathVariable Long consumerId, @Parameter(description = "New consumer status") @RequestParam StatusUser statusUser){
        consumerService.changeStatusById(consumerId, statusUser);
        return ResponseEntity.ok("changed");
    }
}