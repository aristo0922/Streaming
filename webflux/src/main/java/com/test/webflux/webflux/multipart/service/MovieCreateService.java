package com.test.webflux.webflux.multipart.service;

import com.test.webflux.webflux.multipart.data.StreamingResponseBody;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class MovieCreateService {
    public void createMovie(MultipartFile file) {
        if (file.isEmpty()) {
            System.out.println("파일 넣으십셔.");
            return;
        }
        Path filepath = Paths.get("src/main/resources/static", file.getOriginalFilename());

        // 해당 path 에 파일의 스트림 데이터를 저장
        try (OutputStream os = Files.newOutputStream(filepath)) {
            byte[] bytes = file.getBytes();
            Files.write(filepath, bytes);
            System.out.println(" SUCCESS!!! >>> ");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public File watchMovie(int movieId) {
        File file=null;
        if(movieId>=0){
            Path filepath = Paths.get("src/main/resources/static/테스트용.mp4");
            file = new File(filepath.toUri());
        }
        if (!file.exists()) {
            System.out.println("파일이 존재하지 않아!!!");
            return null;
        }

        long fileLength = file.length();

        File finalFile = file;
        return file;
    }
}
