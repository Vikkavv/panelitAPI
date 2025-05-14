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
public class FileStorageService {
    public static final String STR_ROOT_PATH = "C:/Users/victo/OneDrive/Escritorio/TFG/Panelit/BackEnd/resources/";
    public static final String STR_PATH_USER_IMAGES_LOCATION = STR_ROOT_PATH+"images/users";
    public static final String STR_PATH_PANEL_IMAGES_LOCATION = STR_ROOT_PATH+"images/panels";
    public static final String STR_PATH_DOCUMENTS_LOCATION = STR_ROOT_PATH+"documents";
    private final Path rootPanelImagesLocation = Paths.get(STR_PATH_PANEL_IMAGES_LOCATION);
    private final Path rootUserImagesLocation = Paths.get(STR_PATH_USER_IMAGES_LOCATION);
    public final Path rootDocumentsLocation = Paths.get(STR_PATH_DOCUMENTS_LOCATION);

    public String saveUserImage(MultipartFile image) throws IOException {
        if(Files.notExists(rootUserImagesLocation)) {
            Files.createDirectories(rootUserImagesLocation);
        }

        if(image != null && !(image.getContentType().contains("image"))){
            throw new FileUploadException("Only images can be uploaded");
        }

        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename().replace(" ", "_");
        Path destination = rootUserImagesLocation.resolve(Paths.get(fileName)).normalize().toAbsolutePath();

        try(InputStream is = image.getInputStream()) {
            Files.copy(is, destination, StandardCopyOption.REPLACE_EXISTING);
        }

        return fileName;
    }

    public String savePanelImage(MultipartFile image) throws IOException {
        if(Files.notExists(rootPanelImagesLocation)) {
            Files.createDirectories(rootPanelImagesLocation);
        }

        if(image != null && !(image.getContentType().contains("image"))){
            throw new FileUploadException("Only images can be uploaded");
        }

        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename().replace(" ", "_");
        Path destination = rootPanelImagesLocation.resolve(Paths.get(fileName)).normalize().toAbsolutePath();

        try(InputStream is = image.getInputStream()) {
            Files.copy(is, destination, StandardCopyOption.REPLACE_EXISTING);
        }

        return fileName;
    }

    public String savePdfFile(MultipartFile pdf) throws IOException {
        if(Files.notExists(rootDocumentsLocation)) {
            Files.createDirectories(rootDocumentsLocation);
        }

        if(pdf != null && !(pdf.getContentType().contains("pdf"))){
            throw new FileUploadException("Only pdfs can be uploaded");
        }

        String fileName = UUID.randomUUID() + "_" + pdf.getOriginalFilename().replace(" ", "_");
        Path destination = rootDocumentsLocation.resolve(Paths.get(fileName)).normalize().toAbsolutePath();

        try(InputStream is = pdf.getInputStream()) {
            Files.copy(is, destination, StandardCopyOption.REPLACE_EXISTING);
        }

        return fileName;
    }

    public String getPanelImgUrl(String fileName, HttpServletRequest request) {
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        return baseUrl + "/panelImages/" + fileName;
    }

    public String getUserImgUrl(String fileName, HttpServletRequest request) {
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        return baseUrl + "/images/" + fileName;
    }

    public String getDocumentUrl(String fileName, HttpServletRequest request) {
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        return baseUrl + "/documents/" + fileName;
    }
}
