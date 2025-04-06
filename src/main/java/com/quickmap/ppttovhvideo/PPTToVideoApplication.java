package com.quickmap.ppttovhvideo;

import com.quickmap.ppttovhvideo.property.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
    FileStorageProperties.class
})
public class PPTToVideoApplication {

    public static void main(String[] args) {
        SpringApplication.run(PPTToVideoApplication.class, args);
    }
}