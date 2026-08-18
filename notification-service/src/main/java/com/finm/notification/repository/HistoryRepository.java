package com.finm.notification.repository;

import com.finm.notification.domain.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {
    // 계좌 번호별 거래 내역 최신순 조회
    List<History> findByAccountNumberOrderByCreatedAtDesc(Long accountNumber);
}