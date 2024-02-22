package com.quiet.onterviewstorage.file.controller;

import com.quiet.onterviewstorage.file.dto.FileDto;
import com.quiet.onterviewstorage.file.dto.ResourceDto;
import com.quiet.onterviewstorage.file.service.ChunkService;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api-file/chunk")
@Slf4j
public class ChunkController {

    private final ChunkService chunkService;

    @ResponseBody
    @PostMapping("/upload")
    public ResponseEntity<?> chunkUpload(
            @RequestPart("chunk") MultipartFile file,
            @RequestPart("jsonData") FileDto.VideoRequest request
    ) throws IOException {
        HttpStatus uploadStatus = chunkService.chunkUpload(file, request);
        return ResponseEntity.status(uploadStatus).build();
    }

    @GetMapping("/stream/{filename}")
    public ResponseEntity<ResourceRegion> streamVideo(
            @RequestHeader HttpHeaders headers,
            @PathVariable String filename
    ) throws IOException {
        Optional<ResourceDto> isDone = chunkService.getStreamResource(headers,
                filename);

        return isDone.map(resourceDto -> ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .cacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES))
                .contentType(resourceDto.getMediaType())
                .header("Accept-Ranges", "bytes")
                .eTag(resourceDto.getPath())
                .body(resourceDto.getRegion())).orElseGet(() -> ResponseEntity.ok().build());
    }

    @DeleteMapping
    public ResponseEntity<?> chunkDelete(
            @RequestParam("fileName") String fileName
    ) {
        chunkService.delete(fileName);
        return ResponseEntity.noContent().build();
    }
}
