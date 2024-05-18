//package com.subhajit.ApiGateway.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import java.util.Collections;
//
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("*") // Add the origin of your frontend application
//                .allowedMethods("*")
//                .allowedOriginPatterns("*") // Add the origin of your frontend application
//                .allowedHeaders("*");
//    }
//}