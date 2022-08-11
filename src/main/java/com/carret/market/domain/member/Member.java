package com.carret.market.domain.member;

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
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {
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

    private LocalDateTime joinedAt;

    @Builder
    public Member(String email, String password, String name, String nickname,
        Roletype role, String previewUrl, LocalDateTime joinedAt) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.role = role;
        this.previewUrl = previewUrl;
        this.joinedAt = joinedAt;
    }

    public static Member of(String email, String password, String name, String nickname, String previewUrl) {
        return Member.builder()
            .email(email)
            .password(password)
            .name(name)
            .nickname(nickname)
            .previewUrl(previewUrl)
            .role(Roletype.ROLE_MEMBER)
            .joinedAt(LocalDateTime.now())
            .build();
    }

}
