package com.finm.transferservice.domain;

public enum TransferStatus {
    REQUESTED,    // 거래 요청 접수 (기본값)
    COMPLETED,    // 거래 완료
    FAILED,       // 거래 실패 (잔액 부족 등)
    COMPENSATED   // 보상(원복) 트랜잭션 완료
}