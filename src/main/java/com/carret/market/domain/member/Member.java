package com.carret.market.domain.member;

import com.carret.market.support.user.MemberDetail;
import com.carret.market.domain.base.BaseEntity;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.util.ObjectUtils;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity implements MemberDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private int point;

    @Enumerated(EnumType.STRING)
    private Roletype role;

    @Lob
    private String previewUrl;

    private String geolocation;

    @CreatedDate
    private LocalDateTime joinedAt;

    @Builder
    public Member(String email, String password, String name, String nickname,
        Roletype role, String previewUrl, LocalDateTime joinedAt, String geolocation) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.role = role;
        this.previewUrl = previewUrl;
        this.joinedAt = joinedAt;
        this.geolocation = geolocation;
        this.point = 0;
    }

    public void changeInfo(String nickname, String geolocation) {
        if(!ObjectUtils.isEmpty(nickname)) {
            this.nickname = nickname;
        }
        if(!ObjectUtils.isEmpty(geolocation)) {
            this.geolocation = geolocation;
        }
    }

    public void changeProfile(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public void charge(int point) {
        this.point += point;
    }

    public void minusPoint(int point) {
        this.point -= point;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getNickname() {
        return nickname;
    }

    @Override
    public Roletype getRole() {
        return role;
    }

    @Override
    public String getPreviewUrl() {
        return Objects.isNull(previewUrl) ? "/images/common/profile.png" : previewUrl;
    }

    @Override
    public String getGeolocation() {
        return geolocation;
    }

    @Override
    public int getPoint() {
        return point;
    }
}
