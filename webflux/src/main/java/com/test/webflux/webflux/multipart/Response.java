package com.test.webflux.webflux.multipart;

import lombok.Data;

@Data
public class Response {
    String message;
    public Response(String message){
        this.message = message;
    }
}
