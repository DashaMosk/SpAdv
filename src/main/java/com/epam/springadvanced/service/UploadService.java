package com.epam.springadvanced.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by Daria on 22.03.2016.
 */
public interface UploadService {
    void uploadFile(MultipartFile file) throws IOException;
}
