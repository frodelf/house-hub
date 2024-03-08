package com.example.househubadmin.controller;

import com.example.househubadmin.dto.flat.FlatDtoForViewAll;
import com.example.househubadmin.dto.flat.FlatDtoForInformationPage;
import com.example.househubadmin.entity.enums.StatusState;
import com.example.househubadmin.service.FlatService;
import io.minio.errors.*;
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

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Log4j2
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
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @Operation(summary = "The request to get flat by id for information page")
    @GetMapping("/get-by-id/{flatId}")
    public ResponseEntity<FlatDtoForInformationPage> getById(@Parameter(description = "Flat id") @PathVariable Long flatId) throws ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, IOException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return ResponseEntity.ok(flatService.getByIdForInformationPage(flatId));
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authorized"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @Operation(summary = "The request to get all flats for cards")
    @GetMapping("/get-all-for-cards")
    public ResponseEntity<Page<FlatDtoForViewAll>> getAllFlats(@Parameter(description = "Page for pagination") @RequestParam Integer page, @Parameter(description = "Page size for page numbering") @RequestParam Integer pageSize){
        return ResponseEntity.ok(flatService.getAllForCard(page, pageSize));
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authorized"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @Operation(summary = "The request to change flat's status by flat id")
    @PutMapping("/change-status/{flatId}")
    public ResponseEntity<String> changeStatusForFlat(@Parameter(description = "Flat id whose status will be changed") @PathVariable Long flatId, @Parameter(description = "New flat status") @RequestParam StatusState statusState, @Parameter(description = "Fill only if the status is rejected. This is the reason for the rejection") @RequestParam(required = false) String reason){
        flatService.changeStatusByFlatId(flatId, statusState, reason);
        return ResponseEntity.ok("changed");
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authorized"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @Operation(summary = "The request to get all flats by corps id")
    @GetMapping("/get-all-by-corps-id/{corpsId}")
    public ResponseEntity<Page<FlatDtoForViewAll>> getAllByCorpsId(@Parameter(description = "Page for pagination") @RequestParam Integer page, @Parameter(description = "Page size for page numbering") @RequestParam Integer pageSize,  @Parameter(description = "Corps's id for filtration") @PathVariable Long corpsId){
        return ResponseEntity.ok(flatService.getAll(page, pageSize, corpsId));
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authorized"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
    })
    @Operation(summary = "The request to delete the flat by id")
    @DeleteMapping("/delete/{flatId}")
    public ResponseEntity<String> deleteById(@Parameter(description = "Flat id") @PathVariable Long flatId){
        flatService.changeStatusByFlatId(flatId, StatusState.DELETED, null);
        return ResponseEntity.ok("deleted");
    }
}