package com.carret.market.infrastructure.file;

import lombok.Getter;

@Getter
public class UploadFile {

    private final String originalFileName;  // 원본 파일 이름
    private final String storeFileName;     // 저장된 파일 이름
    private final String fileUploadUrl;     // 파일 저장 경로

    public UploadFile(String originalFileName, String storeFileName, String fileUploadUrl) {
        this.originalFileName = originalFileName;
        this.storeFileName = storeFileName;
        this.fileUploadUrl = fileUploadUrl;
    }

}
