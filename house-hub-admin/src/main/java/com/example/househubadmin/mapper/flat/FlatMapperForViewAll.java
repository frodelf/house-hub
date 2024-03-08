package com.example.househubadmin.mapper.flat;

import com.example.househubadmin.dto.flat.FlatDtoForViewAll;
import com.example.househubadmin.entity.Flat;
import com.example.househubadmin.service.MinioService;
import io.minio.errors.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface FlatMapperForViewAll {
    @Mappings({
            @Mapping(target = "images", ignore = true),
            @Mapping(target = "address", ignore = true)
    })
    FlatDtoForViewAll toDto(Flat flat);
    default FlatDtoForViewAll toDto(Flat flat, MinioService minioService) throws ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, IOException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        FlatDtoForViewAll flatDtoForViewAll = toDto(flat);
        if(flat.getImages()!=null && flat.getImages().get(0)!=null) flatDtoForViewAll.setImages(minioService.getUrl(flat.getImages().get(0)));
        if(flat.getCorps()!=null) flatDtoForViewAll.setAddress(flat.getCorps().getBuilding().getAddress());
        return flatDtoForViewAll;
    }
    default Page<FlatDtoForViewAll> toDtoPage(Page<Flat> flats, MinioService minioService){
        return new PageImpl<>(flats.getContent().stream()
                .map(flat -> {
                    try {
                        return toDto(flat, minioService);
                    } catch (ServerException | XmlParserException | InternalException | InvalidResponseException |
                             InvalidKeyException | InsufficientDataException | ErrorResponseException |
                             NoSuchAlgorithmException | IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList()), flats.getPageable(), flats.getTotalElements());
    }
}