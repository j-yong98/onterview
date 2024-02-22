package com.quiet.onterview.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FileUtils {

    @Value("${file.base-url}")
    private String baseUrl;
    @Value("${file.file-key}")
    private String fileKey;
    @Value("${file.file-path-query}")
    private String filePathQuery;
    @Value("${file.file-name-query}")
    private String fileNameQuery;
    @Value("${file.file-path}")
    private String filePath;

    public String thumbnailUrl(String filename) {
        return baseUrl + "/" + fileKey + "?" + filePathQuery + "=" + filePath + "&" + fileNameQuery
                + "=" + filename;
    }
}
