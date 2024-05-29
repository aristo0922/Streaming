package com.test.webflux.webflux.multipart.controller;

import com.test.webflux.webflux.multipart.Response;
import com.test.webflux.webflux.multipart.data.StreamingResponseBody;
import com.test.webflux.webflux.multipart.service.ChunkUploadService;
import com.test.webflux.webflux.multipart.service.MovieCreateService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
@RequiredArgsConstructor
public class ChunkUploadController {
    private final ChunkUploadService chunkUploadService;
    private final MovieCreateService movieCreateService;

    @GetMapping("/chunk")
    public String chunkUploadPage() {
        return "chunk";
    }

    @ResponseBody
    @PostMapping("/chunk/upload")
    public ResponseEntity<String> chunkUpload(@RequestParam("chunk") MultipartFile file,
                                              @RequestParam("chunkNumber") int chunkNumber,
                                              @RequestParam("totalChunks") int totalChunks) throws IOException {
        boolean isDone = chunkUploadService.chunkUpload(file, chunkNumber, totalChunks);
        return isDone ? ResponseEntity.ok("FileUploaded successfully") :
                ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).build();
    }

    @ResponseBody
    @GetMapping("/chunk/upload/{key}")
    public ResponseEntity<?> getLastChunkNumber(@PathVariable String key) throws IOException {
        return ResponseEntity.ok(chunkUploadService.getLastChunkNumber(key));
    }

    @PostMapping("/movie/create")
    public void movieCreator(@RequestParam("movie") MultipartFile file) throws IOException {
        movieCreateService.createMovie(file);
    }

    @GetMapping("/movie/{id}")
    public Resource movieWatcher(@PathVariable("id") int movieId) throws FileNotFoundException{
        return new InputStreamResource(new FileInputStream("src/main/resources/static/테스트용.mp4"));
    }

//    @GetMapping("/movie/{id}")
//    public ResponseEntity<StreamingResponseBody> movieWatcher(@PathVariable("id") int movieId){
//        File file= movieCreateService.watchMovie(movieId);
//        if (!file.isFile()){
//            System.out.println("이거 아니야");
//            return ResponseEntity.notFound().build();
//        }
//
//        StreamingResponseBody streamingResponseBody = outputStream -> FileCopyUtils.copy(new FileInputStream(file), outputStream);
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add("Content-Type", "video/mp4");
//        httpHeaders.add("Content-Length", Long.toString(file.length()));
//        return ResponseEntity.ok().headers(httpHeaders).body(streamingResponseBody);
//    }

//    @GetMapping("/movie/{id}")
//    public ResponseEntity<StreamingResponseBody> movieWatcher(@PathVariable("id") int movieId){
//        File file = movieCreateService.watchMovie(movieId);
//
//        StreamingResponseBody streamingResponseBody = new StreamingResponseBody() {
//            @Override
//            public void writeTo(OutputStream outputStream) throws IOException {
//                try{
//                    final InputStream inputStream=new FileInputStream(file);
//                    byte[] bytes = new byte[1024];
//                    int length;
//                    while((length = inputStream.read(bytes))>=0){
//                        outputStream.write(bytes, 0, length);
//                    }
//                    inputStream.close();
//                    outputStream.flush();
//                }catch (final Exception e){
//                    e.printStackTrace();
//                }
//            }
//        };
//
//
//        final HttpHeaders responseHeader= new HttpHeaders();
//        responseHeader.add("Content-Type", "video/mp4");
//        responseHeader.add("Content-Length", Long.toString(file.length()));
//        return ResponseEntity.ok().headers(responseHeader).body(streamingResponseBody);
//    }


}
