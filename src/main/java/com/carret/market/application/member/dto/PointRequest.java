package com.carret.market.application.member.dto;

import com.carret.market.domain.member.Currency;
import com.carret.market.domain.member.PgType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointRequest {

    @NotBlank(message = "email은 필수값입니다.")
    private String email;

    @NotBlank(message = "description은 필수값입니다.")
    private String description;

    @NotNull(message = "amount은 필수값입니다.")
    private Integer amount;

    @NotNull(message = "currency은 필수값입니다.")
    private Currency currency;

    @NotNull(message = "pgType은 필수값입니다.")
    private PgType pgType;

    @NotBlank(message = "pgTid은 필수값입니다.")
    private String pgTid;

    @NotBlank(message = "merchatUid은 필수값입니다.")
    private String merchatUid;
}
