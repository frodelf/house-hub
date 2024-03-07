package com.example.househubadmin.service.impl;

import com.example.househubadmin.dto.notary.NotaryDtoForAdd;
import com.example.househubadmin.dto.notary.NotaryDtoForViewAll;
import com.example.househubadmin.entity.enums.StatusUser;
import com.example.househubadmin.entity.users.Notary;
import com.example.househubadmin.mapper.notary.NotaryMapperForAdd;
import com.example.househubadmin.mapper.notary.NotaryMapperForViewAll;
import com.example.househubadmin.repository.NotaryRepository;
import com.example.househubadmin.service.MinioService;
import com.example.househubadmin.service.NotaryService;
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
    private final NotaryMapperForViewAll notaryMapperForViewAll;
    private final NotaryMapperForAdd notaryMapperForAdd;
    private final MinioService minioService;
    @Override
    public Page<NotaryDtoForViewAll> getAll(Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Order.desc("id")));
        return notaryMapperForViewAll.toDtoPage(notaryRepository.findAll(pageable), minioService);
    }
    @Override
    public void add(NotaryDtoForAdd notaryDtoForAdd) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        notaryMapperForAdd.updateEntity(notaryDtoForAdd, minioService, this);
    }
    @Override
    public Notary getById(Long id) {
        Notary notary = notaryRepository.findById(id).orElseThrow(
                ()-> {
                    log.error("Notary with id={} not found", id);
                    return new EntityNotFoundException("Notary with id="+id+" not found");
                }
        );
        return notary;
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        Notary notary = getById(id);
        notary.setStatus(StatusUser.REMOVE);
        save(notary);
    }
    @Transactional
    @Override
    public Notary save(Notary notary){
        return notaryRepository.save(notary);
    }
}