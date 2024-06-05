package com.test.webflux.webflux.config;

import lombok.extern.slf4j.Slf4j;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFprobe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Slf4j
@Configuration
public class FFmpegConfig {
    @Value("/opt/homebrew/Cellar/ffmpeg/7.0.1/bin/ffmpeg")
    private String ffmpegPath;

    @Value("/opt/homebrew/Cellar/ffmpeg/7.0.1/bin/ffmpeg")
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