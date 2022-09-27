package com.boot.mybatis20220923nk.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Value("${file.path}")
    private String filePath;

    // file path 를 가지고 이미지 불러오기

    // addResourceHandlers 에서 각종 설정 해줄 수 있다
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);
        registry.addResourceHandler("/image/**") // Builder 형식
                .addResourceLocations("file:///" + filePath) //
                .setCachePeriod(60 * 60) // 60 초 * 60 ; 1 hour 동안 캐시 유지
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {
                    @Override
                    protected Resource getResource(String resourcePath, Resource location) throws IOException {
                        resourcePath = URLDecoder.decode(resourcePath, StandardCharsets.UTF_8);
                        return super.getResource(resourcePath, location);
                    }
                });
    }
}
