package com.example.househubadmin.mapper.notary;

import com.example.househubadmin.dto.notary.NotaryDtoForAdd;
import com.example.househubadmin.entity.users.Notary;
import com.example.househubadmin.service.MinioService;
import com.example.househubadmin.service.NotaryService;
import io.minio.errors.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Mapper(componentModel = "spring")
public interface NotaryMapperForAdd {
    @Mapping(target = "image", ignore = true)
    void updateEntity(NotaryDtoForAdd notaryDtoForAdd, @MappingTarget Notary notary);
    default Notary updateEntity(NotaryDtoForAdd notaryDtoForAdd, MinioService minioService, NotaryService notaryService) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        Notary notary = new Notary();
        if(notaryDtoForAdd.getId()!=null) notary = notaryService.getById(notaryDtoForAdd.getId());
        if(notary.getImage()!=null) minioService.deleteImg(notary.getImage());
        notary.setImage(minioService.putMultipartFile(notaryDtoForAdd.getImage()));
        return notary;
    }
}