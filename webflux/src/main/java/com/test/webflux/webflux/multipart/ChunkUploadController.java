package com.test.webflux.webflux.multipart;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class ChunkUploadController {
    private final ChunkUploadService chunkUploadService;

    @GetMapping("/chunk")
    public String chunkUploadPage(){
        return "chunk";
    }

    @ResponseBody
    @PostMapping("/chunk/upload")
    public ResponseEntity<String> chunkUpload(@RequestParam("chunk") MultipartFile file,
                                              @RequestParam("chunkNumber") int chunkNumber,
                                              @RequestParam("totalChunks") int totalChunks) throws IOException {
        boolean isDone = chunkUploadService.chunkUpload(file, chunkNumber, totalChunks);
        return isDone? ResponseEntity.ok("FileUploaded successfully"):
                ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).build();
    }
    @ResponseBody
    @GetMapping("/chunk/upload/{key}")
    public ResponseEntity<?> getLastChunkNumber(@PathVariable String key) throws IOException {
        return ResponseEntity.ok(chunkUploadService.getLastChunkNumber(key));
    }
}
