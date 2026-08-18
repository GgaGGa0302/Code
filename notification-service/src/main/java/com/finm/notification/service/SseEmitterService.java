package com.finm.notification.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class SseEmitterService {

    // 계좌번호(accountNumber)별 SseEmitter 관리
    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

    // 타임아웃 시간 설정 (60분)
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;

    // SSE 구독 연결
    public SseEmitter subscribe(Long accountNumber) {
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
        emitters.put(accountNumber, emitter);

        // 연결 완료 / 타임아웃 / 에러 발생 시 메모리에서 제거
        emitter.onCompletion(() -> emitters.remove(accountNumber));
        emitter.onTimeout(() -> emitters.remove(accountNumber));
        emitter.onError((e) -> emitters.remove(accountNumber));

        // 최초 연결 시 503 오류 방지를 위한 dummy 이벤트 전송
        try {
            emitter.send(SseEmitter.event()
                    .name("connect")
                    .data("SSE Connected successfully for account: " + accountNumber));
        } catch (IOException e) {
            log.error("Failed to send initial SSE connect event for account: {}", accountNumber, e);
            emitters.remove(accountNumber);
        }

        return emitter;
    }

    // 실시간 알림 메시지 전송
    public void sendNotification(Long accountNumber, Object data) {
        SseEmitter emitter = emitters.get(accountNumber);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event()
                        .name("notification")
                        .data(data));
                log.info("SSE Notification sent to account: {}", accountNumber);
            } catch (IOException e) {
                log.error("Failed to send SSE notification to account: {}", accountNumber, e);
                emitters.remove(accountNumber);
            }
        }
    }
}