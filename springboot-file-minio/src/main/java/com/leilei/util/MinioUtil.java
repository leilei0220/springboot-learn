package com.leilei.util;

import io.minio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MinioUtil {


    @Autowired
    private MinioClient minioClient;

    @Value("${minio.bucket}")
    private String bucket;

    public void upload(String objectName, InputStream in, long size, String contentType) throws Exception {
        minioClient.putObject(PutObjectArgs.builder()
            .bucket(bucket).object(objectName).stream(in, size, -1)
            .contentType(contentType).build());
    }

    public void upload(MultipartFile file, String objectName) throws Exception {
        PutObjectArgs args = PutObjectArgs.builder()
                .bucket(bucket)
                .object(objectName)
                .stream(file.getInputStream(), file.getSize(), -1)
                .contentType(file.getContentType())
                .build();
        minioClient.putObject(args);
    }


    public boolean exists(String objectName) {
        try {
            minioClient.statObject(StatObjectArgs.builder().bucket(bucket).object(objectName).build());
            return true;
        } catch (Exception e) { return false; }
    }

    public void merge(String targetObject, List<String> chunkObjects) throws Exception {
        List<ComposeSource> sources = chunkObjects.stream()
            .map(o -> ComposeSource.builder().bucket(bucket).object(o).build())
            .collect(Collectors.toList());
        minioClient.composeObject(ComposeObjectArgs.builder().bucket(bucket).object(targetObject).sources(sources).build());
    }
}
