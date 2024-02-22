package com.quiet.onterviewstorage.file.dto;

import lombok.*;

public class FileDto {

    @Getter
    @RequiredArgsConstructor
    public static class FileResponse {

        private final String filePath;
        private final String fileName;
    }

    @Getter
    @RequiredArgsConstructor
    public static class VideoRequest {

        private final String filename;
        private final int chunkNumber;
        private final int endOfChunk;
    }
}
