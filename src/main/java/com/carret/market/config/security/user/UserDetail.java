package com.carret.market.config.security.user;

import com.carret.market.domain.member.Member;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
public class UserDetail implements UserDetails {
    private static final String ROLE = "ROLE";
    private static final String DELIMETER = "_";

    private final Member member;
    private final List<GrantedAuthority> authorities = new ArrayList<>();

    public UserDetail(Member member) {
        this.member = member;
        authorities.add(new SimpleGrantedAuthority(String.join(DELIMETER, ROLE, member.getRole().name())));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.member.getPassword();
    }

    @Override
    public String getUsername() {
        return this.member.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return Objects.nonNull(member.getJoinedAt());
    }
}
