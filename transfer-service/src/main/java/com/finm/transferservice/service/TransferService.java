package com.finm.transfer.service;

import com.finm.transfer.dto.DepositRequest;
import com.finm.transfer.dto.TransferRequest;
import com.finm.transfer.dto.TransferResponse;
import com.finm.transfer.dto.WithdrawRequest;

import java.util.List;

public interface TransferService {
    TransferResponse deposit(DepositRequest request);
    TransferResponse withdraw(WithdrawRequest request);
    TransferResponse transfer(TransferRequest request);
    List<TransferResponse> getHistories(String accountNumber);
}