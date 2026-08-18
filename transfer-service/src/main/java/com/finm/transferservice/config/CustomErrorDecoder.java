package com.finm.transferservice.config;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Slf4j
public class CustomErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        String message = "계좌 서비스 호출 중 오류가 발생했습니다.";

        try {
            if (response.body() != null) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.body().asInputStream(), StandardCharsets.UTF_8))) {
                    message = reader.lines().collect(Collectors.joining("\n"));
                }
            }
        } catch (Exception e) {
            log.error("Feign 에러 응답 파싱 실패", e);
        }

        log.error("[Feign Error] methodKey: {}, status: {}, body: {}", methodKey, response.status(), message);

        switch (response.status()) {
            case 400:
                return new ResponseStatusException(HttpStatus.BAD_REQUEST, "요청 오류: " + message);
            case 404:
                return new ResponseStatusException(HttpStatus.NOT_FOUND, "계좌를 찾을 수 없습니다: " + message);
            case 409:
                return new ResponseStatusException(HttpStatus.CONFLICT, "데이터 충돌 오류: " + message);
            default:
                return defaultErrorDecoder.decode(methodKey, response);
        }
    }
}