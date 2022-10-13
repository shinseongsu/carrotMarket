package com.carret.market.infrastructure.file;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.carret.market.global.exception.FileUploadException;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class S3UploadUtils {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public UploadFile uploadFile(MultipartFile multipartFile) {
        if(Objects.isNull(multipartFile)) {
            return null;
        }

        try {
            String s3FileName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();

            ObjectMetadata objMeta = new ObjectMetadata();
            objMeta.setContentLength(multipartFile.getInputStream().available());

            amazonS3Client.putObject(bucket, s3FileName, multipartFile.getInputStream(), objMeta);

            return new UploadFile(multipartFile.getOriginalFilename(), s3FileName,
                amazonS3Client.getUrl(bucket, s3FileName).toString());
        } catch (Exception e) {
            throw new FileUploadException("파일 업로드중 에러가 발생하였습니다.");
        }
    }

    public List<UploadFile> uploadFiles(List<MultipartFile> multipartFiles) {
        return multipartFiles.stream()
            .map(multipartFile ->  this.uploadFile(multipartFile))
            .collect(Collectors.toList());
    }

}
