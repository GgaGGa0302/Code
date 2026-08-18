package com.finm.transferservice.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transfers")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Transfer {

    @Id
    @Column(name = "transfer_id", length = 36, nullable = false, updatable = false)
    private String transferId; // 이체 거래 고유 식별자 (PK)

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", length = 20, nullable = false)
    private TransactionType transactionType; // DEPOSIT / WITHDRAW / TRANSFER

    @Column(name = "from_account", length = 20)
    private String fromAccount; // 출금 계좌번호

    @Column(name = "to_account", length = 20)
    private String toAccount; // 입금 계좌번호

    @Column(name = "amount", nullable = false)
    private Long amount; // 이체 금액 (단위: 원)

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    private TransferStatus status; // REQUESTED / COMPLETED / FAILED / COMPENSATED

    @Column(name = "fail_reason", length = 255)
    private String failReason; // 실패 사유

    @CreationTimestamp
    @Column(name = "requested_at", nullable = false, updatable = false)
    private LocalDateTime requestedAt; // 거래 요청 일시

    @Column(name = "completed_at")
    private LocalDateTime completedAt; // 거래 완료 일시

    @Column(name = "compensated_at")
    private LocalDateTime compensatedAt; // 보상(원복) 완료 일시

    @Builder
    public Transfer(TransactionType transactionType, String fromAccount, String toAccount, Long amount) {
        this.transferId = UUID.randomUUID().toString();
        this.transactionType = transactionType;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.status = TransferStatus.REQUESTED; // 기본값
        this.requestedAt = LocalDateTime.now();
    }

    // 상태 전이 메서드

    // 거래 완료 처리
    public void markCompleted() {
        this.status = TransferStatus.COMPLETED;
        this.completedAt = LocalDateTime.now();
    }

    // 거래 실패 처리
    public void markFailed(String failReason) {
        this.status = TransferStatus.FAILED;
        this.failReason = failReason;
        this.completedAt = LocalDateTime.now();
    }

    // 보상(원복) 처리 (Saga 패턴 실패 보상 시)
    public void markCompensated() {
        this.status = TransferStatus.COMPENSATED;
        this.compensatedAt = LocalDateTime.now();
    }
}