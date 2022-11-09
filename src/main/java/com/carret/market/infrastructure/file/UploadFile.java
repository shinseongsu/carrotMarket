package com.carret.market.infrastructure.file;

import lombok.Getter;

@Getter
public class UploadFile {

    private final String originalFileName;
    private final String storeFileName;
    private final String fileUploadUrl;

    public UploadFile(String originalFileName, String storeFileName, String fileUploadUrl) {
        this.originalFileName = originalFileName;
        this.storeFileName = storeFileName;
        this.fileUploadUrl = fileUploadUrl;
    }

}
