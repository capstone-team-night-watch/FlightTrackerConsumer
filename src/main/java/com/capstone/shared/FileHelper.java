package com.capstone.shared;

import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.file.Files;

public class FileHelper {
    private FileHelper() {
    }


    /**
     * Reads a file from the resources folder
     * @param path path of the file to read from the resource folder
     * @return the string content of the file
     * @throws IOException When the file is not found
     */
    public static String readFile(String path) throws IOException {
        var report = ResourceUtils.getFile("classpath:" + path);

        return new String(Files.readAllBytes(report.toPath()));
    }
}
