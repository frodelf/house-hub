package com.example.househubadmin.mapper.notary;

import com.example.househubadmin.dto.notary.NotaryDtoForViewAll;
import com.example.househubadmin.entity.users.Notary;
import com.example.househubadmin.service.MinioService;
import io.minio.errors.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface NotaryMapperForViewAll {
    @Mapping(target = "image", ignore = true)
    NotaryDtoForViewAll toDto(Notary notary);
    default NotaryDtoForViewAll toDto(Notary notary, MinioService minioService) throws ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, IOException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        NotaryDtoForViewAll notaryDtoForViewAll = toDto(notary);
        notaryDtoForViewAll.setImage(minioService.getUrl(notary.getImage()));
        return notaryDtoForViewAll;
    }
    default Page<NotaryDtoForViewAll> toDtoPage(Page<Notary> notaries, MinioService minioService){
        return new PageImpl<>(notaries.getContent().stream()
                .map(notary -> {
                    try {
                        return toDto(notary, minioService);
                    } catch (ServerException | XmlParserException | InternalException | InvalidResponseException |
                             InvalidKeyException | InsufficientDataException | ErrorResponseException |
                             NoSuchAlgorithmException | IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList()), notaries.getPageable(), notaries.getTotalElements());
    };
}