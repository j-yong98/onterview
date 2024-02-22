package com.quiet.onterviewstorage.util;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFprobe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class FFmpegConfig {

    @Value("${path.ffmpeg}")
    private String ffmpegPath;

    @Value("${path.ffprobe}")
    private String ffprobePath;

    @Bean
    public FFmpeg ffMpeg() throws IOException {
        return new FFmpeg(ffmpegPath);
    }

    @Bean
    public FFprobe ffProbe() throws IOException {
        return new FFprobe(ffprobePath);
    }
}