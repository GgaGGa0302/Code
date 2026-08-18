package com.finm.notification.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long accountNumber;
    private String transactionType;
    private Long amount;
    private Long balanceAfter;
    private String description;

    @Builder.Default
    private boolean isRead = false; // 추가

    @CreatedDate
    private LocalDateTime createdAt;

    // 읽음 처리 비즈니스 메서드 추가
    public void read() {
        this.isRead = true;
    }
}