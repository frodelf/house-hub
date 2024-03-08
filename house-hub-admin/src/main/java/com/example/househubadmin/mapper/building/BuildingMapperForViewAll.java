package com.example.househubadmin.mapper.building;

import com.example.househubadmin.dto.building.BuildingDtoForViewAll;
import com.example.househubadmin.entity.Building;
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
public interface BuildingMapperForViewAll {
    @Mapping(target = "image", ignore = true)
    BuildingDtoForViewAll toDto(Building building);
    default BuildingDtoForViewAll toDto(Building building, MinioService minioService) throws ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, IOException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        BuildingDtoForViewAll buildingDtoForViewAll = toDto(building);
        if(building.getImages()!=null && building.getImages().get(0)!=null)buildingDtoForViewAll.setImage(minioService.getUrl(building.getImages().get(0)));
        return buildingDtoForViewAll;
    }
    default Page<BuildingDtoForViewAll> toDtoPage(Page<Building> buildings, MinioService minioService){
        return new PageImpl<>(buildings.getContent().stream()
                .map(building -> {
                    try {
                        return toDto(building, minioService);
                    } catch (ServerException | XmlParserException | InternalException | InvalidResponseException |
                             InvalidKeyException | InsufficientDataException | ErrorResponseException |
                             NoSuchAlgorithmException | IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList()), buildings.getPageable(), buildings.getTotalElements());
    }
}
