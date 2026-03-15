package com.foodify.backend_foodify.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "dwqaxpz7g",
            "api_key", "939616295163744",
            "api_secret", "s1b8hPSE3_egyZpHvSQKoyVtwHk"
        ));
    }
}