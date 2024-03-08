package com.example.househubadmin.service.impl;

import com.example.househubadmin.dto.user.notary.NotaryDtoForAdd;
import com.example.househubadmin.dto.user.UserDtoForViewAll;
import com.example.househubadmin.entity.enums.StatusUser;
import com.example.househubadmin.entity.users.Notary;
import com.example.househubadmin.mapper.user.notary.NotaryMapperForAdd;
import com.example.househubadmin.mapper.user.UserMapperForViewAll;
import com.example.househubadmin.repository.NotaryRepository;
import com.example.househubadmin.service.MinioService;
import com.example.househubadmin.service.NotaryService;
import com.example.househubadmin.service.UserService;
import io.minio.errors.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Log4j2
@Service
@RequiredArgsConstructor
public class NotaryServiceImpl implements NotaryService {
    private final NotaryRepository notaryRepository;
    private final UserMapperForViewAll userMapperForViewAll;
    private final NotaryMapperForAdd notaryMapperForAdd;
    private final MinioService minioService;
    private final UserService userService;
    @Override
    public Page<UserDtoForViewAll> getAll(Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Order.desc("id")));
        Page<UserDtoForViewAll> result = userMapperForViewAll.toDtoPage(notaryRepository.findAll(pageable), minioService);
        return result;
    }
    @Transactional
    @Override
    public void add(NotaryDtoForAdd notaryDtoForAdd) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        userService.save(notaryMapperForAdd.updateEntity(notaryDtoForAdd, minioService, userService));
    }
    @Transactional
    @Override
    public void deleteById(Long id) {
        Notary notary = (Notary) userService.getById(id);
        notary.setStatus(StatusUser.REMOVE);
        userService.save(notary);
    }
}