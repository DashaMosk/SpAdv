package com.epam.springadvanced.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadService {
    void uploadFile(MultipartFile file) throws IOException;
}
