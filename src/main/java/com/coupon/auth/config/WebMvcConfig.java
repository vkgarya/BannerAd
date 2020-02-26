package com.coupon.auth.config;


import com.coupon.properties.PropertyManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private static final long MAX_AGE_SECS = 3600;
    private static final long MAX_AGE = 30;


    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        CorsRegistration corsRegistration = registry.addMapping("/**");

        corsRegistration.allowedOrigins(getAllowedOrigins());
        corsRegistration.allowedMethods(getAllowedMethods());
        corsRegistration.allowedHeaders(getAllowedHeaders());
        corsRegistration.allowCredentials(true);
        corsRegistration.maxAge(MAX_AGE_SECS);
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/")
                .setCacheControl(CacheControl.maxAge(MAX_AGE, TimeUnit.DAYS));


    }
    private String[] getAllowedOrigins() {
        List<String> allowedOrigins = new ArrayList<>();

        String[] otherAllowedOrigins = PropertyManager.getAllowedCORSOrigins().split(",");


        for (String origin : otherAllowedOrigins) {
            allowedOrigins.add(origin);
        }

        return allowedOrigins.toArray(new String[allowedOrigins.size()]);
    }

    private String[] getAllowedHeaders() {
        List<String> allowedHeaders = new ArrayList<>();

        allowedHeaders.add("x-requested-with");
        allowedHeaders.add("origin");
        allowedHeaders.add("content-type");
        allowedHeaders.add("accept");
        allowedHeaders.add("authorization");
        allowedHeaders.add("at");
        allowedHeaders.add("Custom-Request-Type");

        return allowedHeaders.toArray(new String[allowedHeaders.size()]);
    }

    private String[] getAllowedMethods() {
        List<String> allowedMethods = new ArrayList<>();

        allowedMethods.add("GET");
        allowedMethods.add("POST");
        allowedMethods.add("PUT");
        allowedMethods.add("DELETE");
        allowedMethods.add("OPTIONS");

        return allowedMethods.toArray(new String[allowedMethods.size()]);
    }


}
