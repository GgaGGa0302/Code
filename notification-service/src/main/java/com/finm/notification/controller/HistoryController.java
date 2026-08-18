package com.finm.notification.controller;

import com.finm.notification.dto.NotificationResponseDto;
import com.finm.notification.service.HistoryService;
import com.finm.notification.service.SseEmitterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService historyService;
    private final SseEmitterService sseEmitterService;

    // SSE 실시간 알림 구독
    @GetMapping(value = "/subscribe/{accountNumber}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(@PathVariable Long accountNumber) {
        return sseEmitterService.subscribe(accountNumber);
    }

    // 계좌별 거래 내역 목록 조회
    @GetMapping("/history/{accountNumber}")
    public ResponseEntity<List<NotificationResponseDto>> getHistory(@PathVariable Long accountNumber) {
        List<NotificationResponseDto> response = historyService.getHistoryByAccount(accountNumber);
        return ResponseEntity.ok(response);
    }

    // 읽음 상태 처리 API
    @PatchMapping("/history/{historyId}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable Long historyId) {
        historyService.markAsRead(historyId);
        return ResponseEntity.ok().build();
    }
}