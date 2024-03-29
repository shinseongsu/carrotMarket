package com.carret.market.domain.item;

import com.carret.market.domain.base.BaseEntity;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Lob
    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private boolean thumbnail;

    @Column(nullable = false)
    private String originalName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Item item;

    @Builder
    public ItemImage(String name, String url, boolean thumbnail, String originalName,
        Item item) {
        this.name = name;
        this.url = url;
        this.thumbnail = thumbnail;
        this.originalName = originalName;
        this.item = item;
    }
}
