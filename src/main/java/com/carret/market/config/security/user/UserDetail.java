package com.carret.market.config.security.user;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
public class UserDetail implements UserDetails {
    private static final String BASIC_IMAGE = "/images/common/profile.png";
    private static final String BASIC_IMAGE_PATH = "/images/";

    private final MemberDetail memberDetail;

    public UserDetail(MemberDetail memberDetail) {
        this.memberDetail = memberDetail;
    }

    public String getPreviewUrl() {
        String previewUl = memberDetail.getPreviewUrl();
        return Objects.isNull(previewUl) ? BASIC_IMAGE : BASIC_IMAGE_PATH + previewUl;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(memberDetail.getRole().name()));
    }

    @Override
    public String getPassword() {
        return memberDetail.getPassword();
    }

    @Override
    public String getUsername() {
        return memberDetail.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
