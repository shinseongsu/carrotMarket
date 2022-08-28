package com.carret.market.domain.member;

import com.carret.market.config.security.user.MemberDetail;
import com.carret.market.domain.base.BaseEntity;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
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

    @Enumerated(EnumType.STRING)
    private Roletype role;

    private String previewUrl;

    private String geolocation;

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
    }

    public void changeInfo(String nickname, String previewUrl, String geolocation) {
        if(!ObjectUtils.isEmpty(nickname)) {
            this.nickname = nickname;
        }
        if(!ObjectUtils.isEmpty(previewUrl)) {
            this.previewUrl = previewUrl;
        }
        if(!ObjectUtils.isEmpty(geolocation)) {
            this.geolocation = geolocation;
        }
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
        return previewUrl;
    }

    @Override
    public String getGeolocation() {
        return geolocation;
    }
}
