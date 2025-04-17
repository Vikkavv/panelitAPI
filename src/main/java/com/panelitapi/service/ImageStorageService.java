package com.panelitapi.service;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class ImageStorageService {
    public static final String STR_PATH_IMAGES_LOCATION = "C:/Users/victo/OneDrive/Escritorio/TFG/Panelit/BackEnd/images";
    private final Path rootLocation = Paths.get(STR_PATH_IMAGES_LOCATION);

    public String save(MultipartFile image) throws IOException {
        if(Files.notExists(rootLocation)) {
            Files.createDirectories(rootLocation);
        }

        if(image != null && !(image.getContentType().contains("image"))){
            throw new FileUploadException("Only images can be uploaded");
        }

        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename().replace(" ", "_");
        Path destination = rootLocation.resolve(Paths.get(fileName)).normalize().toAbsolutePath();

        try(InputStream is = image.getInputStream()) {
            Files.copy(is, destination, StandardCopyOption.REPLACE_EXISTING);
        }

        Path ruta = Paths.get(STR_PATH_IMAGES_LOCATION, fileName);
        while (!Files.exists(ruta)) {
            try { Thread.sleep(50); } catch (InterruptedException ignored) {}
        }

        return fileName;
    }

    public String getUrl(String fileName, HttpServletRequest request) {
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        return baseUrl + "/images/" + fileName;
    }
}
