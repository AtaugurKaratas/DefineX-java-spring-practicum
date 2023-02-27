package com.definex.credit.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties
public class AppConfiguration {
    private String uploadProfileImagesPath;

    private String uploadAssetImagesPath;
}
