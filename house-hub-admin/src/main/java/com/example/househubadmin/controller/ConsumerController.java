package com.example.househubadmin.controller;

import com.example.househubadmin.dto.user.UserDtoForViewAll;
import com.example.househubadmin.entity.enums.StatusState;
import com.example.househubadmin.entity.enums.StatusUser;
import com.example.househubadmin.service.ConsumerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    })
    @Operation(summary = "The request to get all consumers")
    @GetMapping("/get-all")
    public ResponseEntity<Page<UserDtoForViewAll>> getAll(@RequestParam Integer page, @RequestParam Integer pageSize, @RequestParam String consumerName){
        return ResponseEntity.ok(consumerService.getAll(page, pageSize, consumerName, StatusUser.ACTIVE));
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authorized"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
    })
    @Operation(summary = "The request to get all blocked consumers")
    @GetMapping("/get-all")
    public ResponseEntity<Page<UserDtoForViewAll>> getAllBlocked(@RequestParam Integer page, @RequestParam Integer pageSize, @RequestParam String consumerName){
        return ResponseEntity.ok(consumerService.getAll(page, pageSize, consumerName, StatusUser.REMOVE));
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authorized"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
    })
    @Operation(summary = "The request to change consumer's status by id")
    @PutMapping("/change-status/{consumerId}")
    public ResponseEntity<String> changeStatusForFlat(@PathVariable Long consumerId, @RequestParam StatusUser statusUser){
        consumerService.changeStatusById(consumerId, statusUser);
        return ResponseEntity.ok("changed");
    }
}