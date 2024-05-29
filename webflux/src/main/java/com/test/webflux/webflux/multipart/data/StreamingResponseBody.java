package com.test.webflux.webflux.multipart.data;

import java.io.OutputStream;

@FunctionalInterface
public interface StreamingResponseBody {
    void writeTo(OutputStream outputStream) throws Exception;
}
