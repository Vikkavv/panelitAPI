package com.panelitapi.config;

import com.panelitapi.service.FileStorageService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class GeneralConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("images/**")
                .addResourceLocations("file:/"+ FileStorageService.STR_PATH_USER_IMAGES_LOCATION+"/");
        registry.addResourceHandler("/panelImages/**")
                .addResourceLocations("file:/"+ FileStorageService.STR_PATH_PANEL_IMAGES_LOCATION+"/");
        registry.addResourceHandler("/documents/**")
                .addResourceLocations("file:/"+ FileStorageService.STR_PATH_DOCUMENTS_LOCATION+"/");
    }
}
