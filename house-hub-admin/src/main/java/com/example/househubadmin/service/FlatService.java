package com.example.househubadmin.service;

import com.example.househubadmin.dto.flat.FlatDtoForViewAll;
import com.example.househubadmin.dto.flat.FlatDtoForInformationPage;
import com.example.househubadmin.entity.Flat;
import com.example.househubadmin.entity.enums.StatusState;
import io.minio.errors.*;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface FlatService {
    Page<FlatDtoForViewAll> getAllForCard(Integer page, Integer pageSize);
    void changeStatusByFlatId(Long id, StatusState statusState, String reason);
    Flat getById(Long id);
    Flat save(Flat flat);
    FlatDtoForInformationPage getByIdForInformationPage(Long id) throws ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, IOException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;
    Page<FlatDtoForViewAll> getAll(Integer page, Integer pageSize, Long corpsId);
}