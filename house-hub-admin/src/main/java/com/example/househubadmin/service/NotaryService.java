package com.example.househubadmin.service;

import com.example.househubadmin.dto.user.notary.NotaryDtoForAdd;
import com.example.househubadmin.dto.user.UserDtoForViewAll;
import com.example.househubadmin.entity.users.Notary;
import io.minio.errors.*;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface NotaryService {
    Page<UserDtoForViewAll> getAll(Integer page, Integer pageSize);
    void add(NotaryDtoForAdd notaryDtoForAdd) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;
    Notary getById(Long id);
    void deleteById(Long id);
    Notary save(Notary notary);
}