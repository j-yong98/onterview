package com.quiet.onterview.file.service;

import com.quiet.onterview.file.dto.request.FileInformationRequest;
import com.quiet.onterview.file.dto.response.FileInformationResponse;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    void saveFileInformation(FileInformationRequest... fileInformationRequests);
    FileInformationResponse loadFileInformationByFileInformationId(Long fileInformationId);
    FileInformationResponse loadFileInformationBySaveFilename(String saveFilename);
    void deleteFileInformationByFileInformationId(Long fileInformationId);
    void deleteFileInformationBySaveName(String saveFilename);
    String transToFileServer(MultipartFile file, String token) throws IOException;
    void deleteFileOnFileServer(String filename, String token);
    void deleteFilesOnFileServer(String token, Long... fileId);
}
