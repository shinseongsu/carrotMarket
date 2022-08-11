package com.carret.market.config.security.user;

import com.carret.market.domain.member.Roletype;
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

    private final String name;
    private final String email;
    private final String password;
    private final String previewUrl;
    private final List<GrantedAuthority> authorities = new ArrayList<>();

    public UserDetail(String name, String email, String password, String previewUrl, Roletype roletype) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.previewUrl = previewUrl;
        authorities.add(new SimpleGrantedAuthority(roletype.name()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.name;
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
