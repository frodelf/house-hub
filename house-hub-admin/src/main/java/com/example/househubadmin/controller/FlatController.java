package com.example.househubadmin.controller;

import com.example.househubadmin.dto.flat.FlatDtoForViewAll;
import com.example.househubadmin.dto.flat.FlatDtoForInformationPage;
import com.example.househubadmin.entity.enums.StatusState;
import com.example.househubadmin.service.FlatService;
import io.minio.errors.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/api/v1/flat")
@RequiredArgsConstructor
@Tag(name = "Flat controller", description = "Flat API")
public class FlatController {
    private final FlatService flatService;
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authorized"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
    })
    @Operation(summary = "The request to get flat by id for information page")
    @GetMapping("/get-by-id/{flatId}")
    public ResponseEntity<FlatDtoForInformationPage> getById(@PathVariable Long flatId) throws ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, IOException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return ResponseEntity.ok(flatService.getByIdForInformationPage(flatId));
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authorized"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
    })
    @Operation(summary = "The request to get all flats for cards")
    @GetMapping("/get-all-for-cards")
    public ResponseEntity<Page<FlatDtoForViewAll>> getAllFlats(Integer page, Integer pageSize){
        return ResponseEntity.ok(flatService.getAllForCard(page, pageSize));
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authorized"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
    })
    @Operation(summary = "The request to change flat's status by flat id")
    @PutMapping("/change-status/{flatId}")
    public ResponseEntity<String> changeStatusForFlat(@PathVariable Long flatId, @RequestParam StatusState statusState, @RequestParam(required = false) String reason){
        flatService.changeStatusByFlatId(flatId, statusState, reason);
        return ResponseEntity.ok("changed");
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authorized"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
    })
    @Operation(summary = "The request to get all flats by corps id")
    @GetMapping("/get-all-by-corps-id/{corpsId}")
    public ResponseEntity<Page<FlatDtoForViewAll>> getAll(@RequestParam Integer page, @RequestParam Integer pageSize, @PathVariable Long corpsId){
        return ResponseEntity.ok(flatService.getAll(page, pageSize, corpsId));
    }
}