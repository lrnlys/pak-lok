package com.rinanzhi.springbootseed.service.validator;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

@Log4j2
@Data
@Service
public class AnswerClassLoader extends ClassLoader {

    public static Path classPath;
    public static final String SEPARATOR = "_";


    public AnswerClassLoader(@Qualifier("classPath") Path classPath) {
        this.classPath = classPath;
    }

    @Override
    protected Class<?> findClass(String className) throws ClassNotFoundException {
        try {
            String[] tokenAndName = className.split(SEPARATOR);
            byte[] data = loadByte(tokenAndName[0], tokenAndName[1]);
            String name = tokenAndName[1];
            return defineClass(name, data, 0, data.length);
        } catch (Exception e) {
            log.error("findClass exception", e);
            throw new ClassNotFoundException();
        }
    }

    public Path getExclusivePath(String executeToken) {
        Path path = Paths.get(classPath.toString(), executeToken);
        if (!path.toFile().exists() && !path.toFile().mkdirs()) {
            throw new RuntimeException("Failed to make directory for class");
        }
        return path;
    }

    private byte[] loadByte(String executeToken, String name) throws Exception {
        name = name.replaceAll("\\.", "/");
        File file = getExclusivePath(executeToken).resolve(name + ".class").toFile();
        return IOUtils.toByteArray(new FileInputStream(file));
    }
}
