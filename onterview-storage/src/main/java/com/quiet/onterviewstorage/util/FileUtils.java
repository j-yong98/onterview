package com.quiet.onterviewstorage.util;

import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FileUtils {

    @Value("${path.image}")
    public String IMAGE_PATH;
    @Value("${path.video}")
    public String VIDEO_PATH;
    @Value("${path.root}")
    public String DEFAULT_URL;
    @Value("${path.default-image}")
    public String DEFAULT_IMAGE;
    final String MP4_EXTENSION = "mp4";
    final String MKV_EXTENSION = "mkv";
    public boolean isVideo(String filename){
        return filename.endsWith(MP4_EXTENSION) || filename.endsWith(MKV_EXTENSION);
    }

    public String createUUIDFileName(String extension) {
        return String.format("%s.%s", UUID.randomUUID(), extension);
    }
}