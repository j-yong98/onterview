package com.quiet.onterviewstorage.file.controller;

import com.quiet.onterviewstorage.file.dto.FileDto;
import com.quiet.onterviewstorage.file.dto.FileDto.FileResponse;
import com.quiet.onterviewstorage.file.service.FileService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api-file")
@Slf4j
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping("/file")
    public ResponseEntity<FileDto.FileResponse> imageUpload(
            @RequestPart("file") MultipartFile image
    ) throws IOException {
        FileResponse response = fileService.imageUpload(image);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/file")
    public ResponseEntity<?> sendFile(
            @RequestParam("filePath") String filePath,
            @RequestParam("fileName") String fileName
    ) {
        Resource image = fileService.getImage(filePath, fileName);
        return ResponseEntity.ok()
                .body(image);
    }

    @DeleteMapping("/file")
    public ResponseEntity<?> deleteFile(
            @RequestParam("filePath") String filePath,
            @RequestParam("fileName") String fileName
    ) {
        fileService.deleteImage(filePath, fileName);
        return ResponseEntity.noContent().build();
    }
}