package com.finm.transfer.service.impl;

import com.finm.transfer.domain.TransactionType;
import com.finm.transfer.domain.Transfer;
import com.finm.transfer.dto.DepositRequest;
import com.finm.transfer.dto.TransferRequest;
import com.finm.transfer.dto.TransferResponse;
import com.finm.transfer.dto.WithdrawRequest;
import com.finm.transfer.repository.TransferRepository;
import com.finm.transfer.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TransferServiceImpl implements TransferService {

    private final TransferRepository transferRepository;

    @Override
    @Transactional
    public TransferResponse deposit(DepositRequest request) {
        Transfer transfer = Transfer.builder()
                .transactionType(TransactionType.DEPOSIT)
                .fromAccount(null)
                .toAccount(request.getToAccount())
                .amount(request.getAmount())
                .build();

        transfer.markCompleted();
        transferRepository.save(transfer);
        return TransferResponse.from(transfer);
    }

    @Override
    @Transactional
    public TransferResponse withdraw(WithdrawRequest request) {
        Transfer transfer = Transfer.builder()
                .transactionType(TransactionType.WITHDRAW)
                .fromAccount(request.getFromAccount())
                .toAccount(null)
                .amount(request.getAmount())
                .build();

        transfer.markCompleted();
        transferRepository.save(transfer);
        return TransferResponse.from(transfer);
    }

    @Override
    @Transactional
    public TransferResponse transfer(TransferRequest request) {
        Transfer transfer = Transfer.builder()
                .transactionType(TransactionType.TRANSFER)
                .fromAccount(request.getFromAccount())
                .toAccount(request.getToAccount())
                .amount(request.getAmount())
                .build();

        transfer.markCompleted();
        transferRepository.save(transfer);
        return TransferResponse.from(transfer);
    }

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