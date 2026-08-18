package com.finm.notification.controller;

import com.finm.notification.dto.NotificationResponseDto;
import com.finm.notification.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService historyService;

    // 계좌별 거래 내역 목록 조회
    @GetMapping("/history/{accountNumber}")
    public ResponseEntity<List<NotificationResponseDto>> getHistory(@PathVariable Long accountNumber) {
        List<NotificationResponseDto> response = historyService.getHistoryByAccount(accountNumber);
        return ResponseEntity.ok(response);
    }
}