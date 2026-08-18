package com.finm.account.ledger.dto;

import com.finm.account.ledger.entity.AccountEntity;
import com.finm.account.ledger.entity.AccountStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "계좌 응답")
public class AccountResponseDto {

    @Schema(description = "계좌 ID", example = "10")
    private Long accountId;

    @Schema(description = "계좌번호", example = "110-001-1111")
    private String accountNumber;

    @Schema(description = "잔액", example = "10000")
    private Long balance;

    @Schema(description = "계좌 상태", example = "ACTIVE")
    private AccountStatus accountStatus;

    /**
     * AccountEntity -> AccountResponseDto 변환 메서드
     */
    public static AccountResponseDto fromEntity(AccountEntity entity) {
        return AccountResponseDto.builder()
                .accountId(entity.getAccountId())
                .accountNumber(entity.getAccountNumber())
                .balance(entity.getBalance())
                .accountStatus(entity.getAccountStatus())
                .build();
    }
}