package com.carret.market.domain.member;

import com.carret.market.domain.base.BaseEntity;
import java.time.LocalDateTime;
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

    private String password;
    private String name;
    private String email;
    private String nickname;

    @Enumerated(EnumType.STRING)
    private Roletype role;

    private boolean emailVerified;
    private String emailCheckToken;
    private LocalDateTime emailCheckTokenGeneratedAt;
    private LocalDateTime joinedAt;

    private String profileImage;

    @Builder
    public Member(Long id, String password, String name, String email, String nickname,
        Roletype role, boolean emailVerified, String emailCheckToken,
        LocalDateTime emailCheckTokenGeneratedAt, LocalDateTime joinedAt, String profileImage) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
        this.nickname = nickname;
        this.role = role;
        this.emailVerified = emailVerified;
        this.emailCheckToken = emailCheckToken;
        this.emailCheckTokenGeneratedAt = emailCheckTokenGeneratedAt;
        this.joinedAt = joinedAt;
        this.profileImage = profileImage;
    }

}
