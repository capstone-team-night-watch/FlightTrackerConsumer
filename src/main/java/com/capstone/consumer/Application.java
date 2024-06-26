package com.capstone.consumer;

import com.capstone.geocode.ReverseGeoCode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Application class that handles the kicking off the execution of the project
 */
@SpringBootApplication
@EnableScheduling
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * Configurer to set up the Cors environment
     *
     * @return a WebMvcConfigurer Object that is used by Spring when configuring the project
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {

            /**
             * Adds the Cors mapping to the registry to allow all cross-origin requests
             * This is needed due to not having time to set up the necessary component to truly handle all the cross-origin requests being made
             * @param registry The Spring-provided CorsRegistry
             */
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("*").allowedOrigins("*");
            }
        };
    }

    @Bean
    public ReverseGeoCode getReverseGeoCoder(ApplicationProperties applicationProperties) throws IOException {
        var fileIdentifier = ResourceUtils.getFile(applicationProperties.getGeonamePath());
        var file = new FileInputStream(fileIdentifier);

        return new ReverseGeoCode(file, true);
    }
}
