package com.carret.market.domain.member;

import com.carret.market.domain.base.BaseTimeEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Point extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int amount;

    @Enumerated(EnumType.STRING)
    private PgType pgType;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(nullable = false)
    private String pgTid;

    @Column(nullable = false)
    private String merchantId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Point(String description, int amount, PgType pgType, Currency currency, String pgTid,
        String merchantId, Member member) {
        this.description = description;
        this.amount = amount;
        this.pgType = pgType;
        this.currency = currency;
        this.pgTid = pgTid;
        this.merchantId = merchantId;
        this.member = member;
    }

}
