package com.carret.market.domain.item;

import com.carret.market.domain.base.BaseEntity;
import com.carret.market.domain.member.Member;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String detail;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private String location;

    @ColumnDefault("0")
    private Integer viewCount;

    @Enumerated(EnumType.STRING)
    private ItemStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Item(String title, String detail, Integer price,
        Category category, String location, ItemStatus status,
        Member member, Integer viewCount) {
        this.title = title;
        this.detail = detail;
        this.price = price;
        this.category = category;
        this.location = location;
        this.status = status;
        this.member = member;
        this.viewCount = viewCount;
    }

    public void updateItem(String title, String detail, int price, Category category) {
        this.title = title;
        this.detail = detail;
        this.price = price;
        this.category = category;
    }

    public void viewCountIncrease() {
        this.viewCount += 1;
    }

    public void sold() {
        status = ItemStatus.SOLD;
    }
}
