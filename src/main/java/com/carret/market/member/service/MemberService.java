package com.carret.market.member.service;

import com.carret.market.member.domain.Member;
import com.carret.market.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email)
            .orElseThrow(IllegalAccessError::new);
    }

}
