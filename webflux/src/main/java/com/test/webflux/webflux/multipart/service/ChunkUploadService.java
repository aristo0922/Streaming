package com.test.webflux.webflux.multipart.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

@Service
public class ChunkUploadService {
    public boolean chunkUpload(MultipartFile file, int chunkNumber, int totalChunks) throws IOException{
        String uploadDir = "video";
        File dir = new File(uploadDir);
        if(!dir.exists()) dir.mkdirs();

        String filename = file.getOriginalFilename() + ".part" + chunkNumber;
        Path filePath = Paths.get(uploadDir, filename);
        Files.write(filePath, file.getBytes());

        if(chunkNumber == totalChunks -1){
            String [] split = file.getOriginalFilename().split("\\.");
            String outputFilename = UUID.randomUUID()+"."+split[split.length-1];
            Path outputFile = Paths.get(uploadDir, outputFilename);
            Files.createFile(outputFile);

            for(int i=0;i<totalChunks;i++){
                Path chunkFile = Paths.get(uploadDir, file.getOriginalFilename()+".part"+i);
                Files.write(outputFile, Files.readAllBytes(chunkFile), StandardOpenOption.APPEND);
                Files.delete(chunkFile);
            }
            return true;
        }
        return false;
    }

    public int getLastChunkNumber(String key) {
        Path temp = Paths.get("video", key);
        String[] list = temp.toFile().list();
        return list == null? 0:Math.max(list.length-2,0);
    }
}
