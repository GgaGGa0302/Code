package com.finm.transferservice.service;

import com.finm.transferservice.dto.DepositRequest;
import com.finm.transferservice.dto.TransferRequest;
import com.finm.transferservice.dto.TransferResponse;
import com.finm.transferservice.dto.WithdrawRequest;

import java.util.List;

public interface TransferService {
    TransferResponse deposit(DepositRequest request);
    TransferResponse withdraw(WithdrawRequest request);
    TransferResponse transfer(TransferRequest request);
    List<TransferResponse> getHistories(String accountNumber);
}