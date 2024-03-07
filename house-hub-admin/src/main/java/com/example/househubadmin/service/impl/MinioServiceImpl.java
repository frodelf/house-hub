package com.example.househubadmin.service.impl;

import com.example.househubadmin.service.MinioService;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class MinioServiceImpl implements MinioService {
    private final MinioClient minioClient;
    private final String bucketName = "house-hub";
    public byte[] getPhoto(String objectName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        log.info("MinioServiceImpl-getPhoto start");
        InputStream objectStream = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object("/" + objectName)
                        .build());

        byte[] photoBytes = IOUtils.toByteArray(objectStream);

        objectStream.close();

        log.info("MinioServiceImpl-getPhoto successfully");
        return photoBytes;
    }
    public void deleteImg(String objectName) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, NoSuchAlgorithmException, ServerException, XmlParserException, IOException {
        log.info("MinioServiceImpl-deleteImg start");
        minioClient.removeObject(RemoveObjectArgs.builder()
                .bucket(bucketName)
                .object("/" + objectName)
                .build());
        log.info("MinioServiceImpl-deleteImg successfully");
    }
    public String putMultipartFile(MultipartFile multipartFile) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        log.info("MinioServiceImpl-putMultipartFile start");
        String nameFile = UUID.randomUUID() + "." + multipartFile.getOriginalFilename();

        File tempFile = File.createTempFile("tempfile", nameFile);
        multipartFile.transferTo(tempFile);

        minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucketName)
                .object("/"+nameFile)
                .stream(new FileInputStream(tempFile), tempFile.length(), -1)
                .contentType(multipartFile.getContentType())
                .build());

        tempFile.delete();
        log.info("MinioServiceImpl-putMultipartFile successfully");
        return nameFile;
    }
    public String getUrl(String fileName) throws ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, IOException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        log.info("MinioServiceImpl-getUrl start");
        String url = "data:image/jpeg;base64, " + Base64.getEncoder().encodeToString(getPhoto(fileName));
        log.info("MinioServiceImpl-getUrl successfully");
        return url;
    }

    @Override
    public List<String> getUrl(List<String> fileNames) throws ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, IOException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        log.info("MinioServiceImpl-getUrl start");
        List<String> result = new ArrayList<>();
        for (String fileName : fileNames) {
            result.add(getUrl(fileName));
        }
        log.info("MinioServiceImpl-getUrl successfully");
        return result;
    }

}