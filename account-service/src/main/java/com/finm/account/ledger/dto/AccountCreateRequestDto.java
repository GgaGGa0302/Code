package com.finm.account.ledger.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "계좌 개설 요청")
public class AccountCreateRequestDto {

    @NotNull(message = "사용자 ID는 필수입니다.")
    @Schema(description = "사용자 ID", example = "1")
    private Long userId;

    @NotNull(message = "초기 잔액은 필수입니다.")
    @Min(value = 0, message = "잔액은 0 이상이어야 합니다.")
    @Schema(description = "초기 잔액", example = "10000")
    private Long balance;
}
