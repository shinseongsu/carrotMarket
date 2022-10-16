package com.carret.market.domain.chat;

import com.carret.market.domain.item.Item;
import com.carret.market.domain.member.Member;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    private Item item;

    @OneToOne(optional = false)
    private Member buyer;

    public Room(Item item, Member buyer) {
        this.item = item;
        this.buyer = buyer;
    }
}
