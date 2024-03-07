package com.example.househubadmin.mapper.flat;

import com.example.househubadmin.dto.flat.FlatDtoForInformationPage;
import com.example.househubadmin.entity.Flat;
import com.example.househubadmin.service.MinioService;
import io.minio.errors.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Mapper(componentModel = "spring")
public interface FlatMapperForInformationPage {
    @Mappings({
            @Mapping(target = "images", ignore = true),
            @Mapping(target = "address", ignore = true),
            @Mapping(target = "notaryName", ignore = true),
            @Mapping(target = "notaryImage", ignore = true)
    })
    FlatDtoForInformationPage toDto(Flat flat);
    default FlatDtoForInformationPage toDto(Flat flat, MinioService minioService) throws ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, IOException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        FlatDtoForInformationPage flatDtoForInformationPage = toDto(flat);
        if(flat.getImages()!=null)flatDtoForInformationPage.setImages(minioService.getUrl(flat.getImages()));
        if(flat.getCorps()!=null)flatDtoForInformationPage.setAddress(flat.getCorps().getBuilding().getAddress());
        if(flat.getNotary()!=null){
            flatDtoForInformationPage.setNotaryName(flat.getNotary().getName());
            flatDtoForInformationPage.setNotaryImage(minioService.getUrl(flat.getNotary().getImage()));
        }
        return flatDtoForInformationPage;
    }
}