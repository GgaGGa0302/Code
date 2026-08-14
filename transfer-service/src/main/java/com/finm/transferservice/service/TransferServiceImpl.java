package com.finm.transfer.service.impl;

import com.finm.transfer.domain.TransactionType;
import com.finm.transfer.domain.Transfer;
import com.finm.transfer.dto.DepositRequest;
import com.finm.transfer.dto.TransferRequest;
import com.finm.transfer.dto.TransferResponse;
import com.finm.transfer.dto.WithdrawRequest;
import com.finm.transfer.kafka.TransferProducerService;
import com.finm.transfer.repository.TransferRepository;
import com.finm.transfer.service.TransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TransferServiceImpl implements TransferService {

    private static final String TOPIC_TRANSFER_EVENTS = "transfer-events";

    private final TransferRepository transferRepository;
    private final TransferProducerService transferProducerService;

    // 현금 입금 처리
    @Override
    @Transactional
    public TransferResponse deposit(DepositRequest request) {
        // 엔티티 생성 (초기 상태 - REQUESTED)
        Transfer transfer = Transfer.builder()
                .transactionType(TransactionType.DEPOSIT)
                .fromAccount(null)
                .toAccount(request.getToAccount())
                .amount(request.getAmount())
                .build();

        // account-service FeignClient 호출로 입금 잔액 증가 처리

        // 거래 완료 처리 및 DB 저장
        transfer.markCompleted();
        Transfer savedTransfer = transferRepository.save(transfer);
        TransferResponse response = TransferResponse.from(savedTransfer);

        // Kafka 이벤트 발행 (비동기 알림 및 타 서비스 전파)
        transferProducerService.send(TOPIC_TRANSFER_EVENTS, response);

        log.info("[TransferService] Deposit completed: transferId={}", response.getTransferId());
        return response;
    }

    // 현금 출금 처리
    @Override
    @Transactional
    public TransferResponse withdraw(WithdrawRequest request) {
        // 엔티티 생성
        Transfer transfer = Transfer.builder()
                .transactionType(TransactionType.WITHDRAW)
                .fromAccount(request.getFromAccount())
                .toAccount(null)
                .amount(request.getAmount())
                .build();

        // account-service FeignClient 호출로 잔액 검증 및 차감 처리

        // 거래 완료 처리 및 DB 저장
        transfer.markCompleted();
        Transfer savedTransfer = transferRepository.save(transfer);
        TransferResponse response = TransferResponse.from(savedTransfer);

        // Kafka 이벤트 발행
        transferProducerService.send(TOPIC_TRANSFER_EVENTS, response);

        log.info("[TransferService] Withdraw completed: transferId={}", response.getTransferId());
        return response;
    }

    // 계좌 간 이체 처리
    @Override
    @Transactional
    public TransferResponse transfer(TransferRequest request) {
        // 엔티티 생성
        Transfer transfer = Transfer.builder()
                .transactionType(TransactionType.TRANSFER)
                .fromAccount(request.getFromAccount())
                .toAccount(request.getToAccount())
                .amount(request.getAmount())
                .build();

        // account-service FeignClient 호출 (출금 계좌 차감 -> 입금 계좌 증가)

        // 거래 완료 처리 및 DB 저장
        transfer.markCompleted();
        Transfer savedTransfer = transferRepository.save(transfer);
        TransferResponse response = TransferResponse.from(savedTransfer);

        // Kafka 이벤트 발행
        transferProducerService.send(TOPIC_TRANSFER_EVENTS, response);

        log.info("[TransferService] Transfer completed: transferId={}", response.getTransferId());
        return response;
    }

    // 거래 내역 조회
    @Override
    public List<TransferResponse> getHistories(String accountNumber) {
        List<Transfer> list;
        if (accountNumber != null && !accountNumber.isBlank()) {
            list = transferRepository.findByFromAccountOrToAccountOrderByRequestedAtDesc(accountNumber, accountNumber);
        } else {
            list = transferRepository.findAll();
        }

        return list.stream()
                .map(TransferResponse::from)
                .collect(Collectors.toList());
    }
}