package com.carret.market.application.member;

import static com.carret.market.global.exception.ErrorCode.NOT_FOUND_USERNAME;

import com.carret.market.global.exception.ErrorCode;
import com.carret.market.support.user.MemberDetail;
import com.carret.market.support.user.UserDetail;
import com.carret.market.domain.member.Member;
import com.carret.market.domain.member.MemberRepository;
import com.carret.market.application.auth.AuthUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginMemberService implements AuthUserDetailsService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException(NOT_FOUND_USERNAME.getMessage()));

        return new UserDetail((MemberDetail) member );
    }

}
