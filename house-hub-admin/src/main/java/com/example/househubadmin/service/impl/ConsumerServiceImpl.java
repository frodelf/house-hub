package com.example.househubadmin.service.impl;

import com.example.househubadmin.dto.user.UserDtoForViewAll;
import com.example.househubadmin.entity.enums.StatusUser;
import com.example.househubadmin.entity.users.Consumer;
import com.example.househubadmin.mapper.user.UserMapperForViewAll;
import com.example.househubadmin.repository.ConsumerRepository;
import com.example.househubadmin.service.ConsumerService;
import com.example.househubadmin.service.MinioService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
public class ConsumerServiceImpl implements ConsumerService {
    private final ConsumerRepository consumerRepository;
    private final UserMapperForViewAll userMapperForViewAll;
    private final MinioService minioService;
    @Override
    public Page<UserDtoForViewAll> getAll(Integer page, Integer pageSize, String consumerName, StatusUser statusUser) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Order.desc("id")));
        return userMapperForViewAll.toDtoPage(consumerRepository.findAllByNameLikeAndStatus(consumerName, statusUser, pageable), minioService);
    }
    @Transactional
    @Override
    public void changeStatusById(Long consumerId, StatusUser statusUser) {
        Consumer consumer = getById(consumerId);
        consumer.setStatus(statusUser);
        save(consumer);
    }

    @Override
    public Consumer getById(Long id) {
        return consumerRepository.findById(id).orElseThrow(
                ()-> {
                    log.error("Consumer with id={} not found", id);
                    return new EntityNotFoundException("Consumer with id="+id+" not found");
                }
        );
    }
    @Transactional
    @Override
    public Consumer save(Consumer consumer) {
        return consumerRepository.save(consumer);
    }
}