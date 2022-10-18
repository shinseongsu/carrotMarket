package com.carret.market.support.user;

import com.carret.market.application.auth.AuthUserDetailsService;
import com.carret.market.domain.member.Member;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailService {

    private final AuthUserDetailsService authUserDetailsService;

    public void memberInfoChange(Member member) {
        UserDetails userDetails = authUserDetailsService.loadUserByUsername(member.getEmail());
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(member.getRole().name()));

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, member.getPassword(), authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
