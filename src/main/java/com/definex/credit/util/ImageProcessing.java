package com.definex.credit.util;

import com.definex.credit.config.AppConfiguration;
import com.definex.credit.model.enumeration.ImageType;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Base64;
import java.util.UUID;

@Component
@Slf4j
public class ImageProcessing {
    private final AppConfiguration appConfiguration;

    public ImageProcessing(AppConfiguration appConfiguration) {
        this.appConfiguration = appConfiguration;
    }

    public String writeBase64EncodeStringToFileForImages(String image, ImageType imageType) {
        String fileName = generateRandomUUID();
        Tika tika = new Tika();
        byte[] base64Encoded = Base64.getDecoder().decode(image);
        String mediaType = tika.detect(base64Encoded);
        mediaType = mediaType.split("/")[1];
        File target = selectImageType(imageType, fileName, mediaType);
        try {
            OutputStream outputStream = new FileOutputStream(target);
            outputStream.write(base64Encoded);
            outputStream.close();
        } catch (Exception e) {
            log.info(fileName);
        }
        return (fileName + "." + mediaType);
    }

    public String imageProcess(String imagePath, ImageType imageType) {
        try {
            if (imagePath == null) return null;
            return writeBase64EncodeStringToFileForImages(imagePath, imageType);
        } catch (IllegalArgumentException e) {
            log.warn(e.toString());
            return imagePath;
        }
    }

    public File selectImageType(ImageType imageType, String fileName, String mediaType) {
        return switch (imageType) {
            case PROFILE -> new File(appConfiguration.getUploadProfileImagesPath() + "/" + fileName + "." + mediaType);
            case ASSET -> new File(appConfiguration.getUploadAssetImagesPath() + "/" + fileName + "." + mediaType);
        };
    }

    public String generateRandomUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
