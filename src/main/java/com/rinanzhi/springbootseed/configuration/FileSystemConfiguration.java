package com.rinanzhi.springbootseed.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class FileSystemConfiguration {

    @Bean("tempPath")
    public Path tempPath() throws URISyntaxException {
        return Paths.get(FileSystemConfiguration.class.getClassLoader().getResource("").toURI());
    }

    @Bean("classPath")
    public Path classPath() throws URISyntaxException {
        return tempPath().resolve("class");
    }

}
