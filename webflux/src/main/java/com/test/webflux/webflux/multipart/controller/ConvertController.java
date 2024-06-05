package com.test.webflux.webflux.multipart.controller;

import com.test.webflux.webflux.multipart.service.ConvertService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ConvertController {

    private final ConvertService convertService;

    @ResponseBody
    @PostMapping("/convert/hls/{filename}")
    public String convertToHls(
            @PathVariable String filename
    ) {
        convertService.convertToHls(filename);
        return "success";
    }
}