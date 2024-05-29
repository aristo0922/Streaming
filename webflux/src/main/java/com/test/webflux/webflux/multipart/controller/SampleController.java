package com.test.webflux.webflux.multipart.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/file")
public class SampleController {
    @GetMapping("/hello")
    public String hello(){
        return"hello world fux";
    }

    @GetMapping
    public String getFile(Model model){
        return "/files/file";
    }

    @PostMapping
    public String postFile(@RequestParam MultipartFile file, RedirectAttributes redirectAttributes) throws IOException{
        // file 을 저장소에 저장하는 코드
        String fileName = file.getOriginalFilename();

        File destinationFile = new File("/Users/aristo/Desktop/2024/Streaming/webflux/src/main/resources/static/"+fileName);
        destinationFile.getParentFile().mkdir();
        file.transferTo(destinationFile); // 이 메소드에 의해 저장 경로에 실질적으로 File 이 생성된다.

        //upload Response Message
        String message= fileName+"is uploded";
        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/file";
    }
}
