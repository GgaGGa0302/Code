package com.finm.transfer.controller;

import com.finm.transfer.dto.DepositRequest;
import com.finm.transfer.dto.TransferRequest;
import com.finm.transfer.dto.TransferResponse;
import com.finm.transfer.dto.WithdrawRequest;
import com.finm.transfer.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transfers")
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;

    // 1. 현금 입금 API
    // POST /api/transfers/deposit
    @PostMapping("/deposit")
    public ResponseEntity<TransferResponse> deposit(@RequestBody DepositRequest request) {
        TransferResponse response = transferService.deposit(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 현금 출금 API
    // POST /api/transfers/withdraw
    @PostMapping("/withdraw")
    public ResponseEntity<TransferResponse> withdraw(@RequestBody WithdrawRequest request) {
        TransferResponse response = transferService.withdraw(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 계좌 간 이체 API
    // POST /api/transfers
    @PostMapping
    public ResponseEntity<TransferResponse> transfer(@RequestBody TransferRequest request) {
        TransferResponse response = transferService.transfer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 거래 내역 조회 API
    // GET /api/transfers?accountNumber=110-001-1111
    @GetMapping
    public ResponseEntity<List<TransferResponse>> getTransferHistories(
            @RequestParam(name = "accountNumber", required = false) String accountNumber) {
        List<TransferResponse> histories = transferService.getHistories(accountNumber);
        return ResponseEntity.ok(histories);
    }
}