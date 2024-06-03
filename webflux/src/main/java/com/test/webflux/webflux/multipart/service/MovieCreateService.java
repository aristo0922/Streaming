package com.test.webflux.webflux.multipart.service;

import com.test.webflux.webflux.multipart.data.StreamingResponseBody;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

@Service
public class MovieCreateService{
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

    public ResponseEntity<ResourceRegion> streamingVideo(HttpHeaders httpHeaders, int movieId) {
        if(movieId<0) return null;
        // id 랑 매칭해서 경로 불러오기
        Path filepath = Paths.get("src/main/resources/static/테스트용.mp4");
        File file = new File(filepath.toUri());
        Resource resource = new FileSystemResource(filepath);
        try{
            long chunkSize = 1024*1024;
            long contentLength=resource.contentLength();
            ResourceRegion resourceRegion;
            try{
                HttpRange httpRange;
                if(httpHeaders.getRange().stream().findFirst().isPresent()){
                    httpRange = httpHeaders.getRange().stream().findFirst().get();
                    long start = httpRange.getRangeStart(contentLength);
                    long end = httpRange.getRangeEnd(contentLength);
                    long rangeLength = Long.min(chunkSize, end-start+1);

                    resourceRegion = new ResourceRegion(resource, start, rangeLength);
                }
                else{
                    resourceRegion = new ResourceRegion(resource, 0, Long.min(chunkSize, resource.contentLength()));
                }
            }catch (Exception e){
                e.printStackTrace();
                long rangeLength = Long.min(chunkSize, resource.contentLength());
                resourceRegion=new ResourceRegion(resource, 0, rangeLength);
            }
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                    .cacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES))
                    .contentType(MediaTypeFactory.getMediaType(resource).orElse(MediaType.APPLICATION_OCTET_STREAM))
                    .header(HttpHeaders.ACCEPT_RANGES, "bytes")
                    .body(resourceRegion);
        } catch(Exception e){
            e.printStackTrace();;
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }
}
