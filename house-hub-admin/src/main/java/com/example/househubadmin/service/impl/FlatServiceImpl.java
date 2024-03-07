package com.example.househubadmin.service.impl;

import com.example.househubadmin.dto.flat.FlatDtoForViewAll;
import com.example.househubadmin.dto.flat.FlatDtoForInformationPage;
import com.example.househubadmin.entity.Flat;
import com.example.househubadmin.entity.enums.StatusState;
import com.example.househubadmin.mapper.flat.FlatMapperForInformationPage;
import com.example.househubadmin.mapper.flat.FlatMapperForViewAll;
import com.example.househubadmin.repository.FlatRepository;
import com.example.househubadmin.service.FlatService;
import com.example.househubadmin.service.MinioService;
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
public class FlatServiceImpl implements FlatService {
    private final MinioService minioService;
    private final FlatRepository flatRepository;
    private final FlatMapperForViewAll flatMapperForViewAll;
    private final FlatMapperForInformationPage flatMapperForInformationPage;
    @Override
    public Page<FlatDtoForViewAll> getAllForCard(Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Order.desc("id")));
        return flatMapperForViewAll.toDtoPage(flatRepository.findAllByStatusState(StatusState.UPDATED, pageable), minioService);
    }
    @Override
    @Transactional
    public void changeStatusByFlatId(Long id, StatusState statusState, String reason) {
        Flat flat = getById(id);
        flat.setStatusState(statusState);
        if(reason!=null)flat.setReason(reason);
        save(flat);
    }
    @Override
    public Flat getById(Long id) {
        Flat flat = flatRepository.findById(id).orElseThrow(
                ()-> {
                    log.error("Flat with id={} not found", id);
                    return new EntityNotFoundException("Flat with id="+id+" not found");
                }
        );
        return flat;
    }
    @Override
    @Transactional
    public Flat save(Flat flat) {
        return flatRepository.save(flat);
    }
    @Override
    public FlatDtoForInformationPage getByIdForInformationPage(Long id) throws ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, IOException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return flatMapperForInformationPage.toDto(getById(id), minioService);
    }
}