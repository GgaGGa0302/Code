package com.finm.transfer.repository;

import com.finm.transfer.domain.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, String> {

    // 특정 계좌가 관여된(출금 또는 입금) 모든 내역 최신 거래순 조회
    List<Transfer> findByFromAccountOrToAccountOrderByRequestedAtDesc(String fromAccount, String toAccount);
}