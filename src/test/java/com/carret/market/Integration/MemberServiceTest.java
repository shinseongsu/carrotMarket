package com.carret.market.Integration;

import static org.assertj.core.api.Assertions.assertThat;

import com.carret.market.domain.member.Member;
import com.carret.market.domain.member.MemberRepository;
import com.carret.market.domain.member.Roletype;
import com.carret.market.global.exception.ErrorCode;
import com.carret.market.global.exception.MemberNotFoundException;
import com.carret.market.application.member.MemberService;
import com.carret.market.application.member.dto.MemberChange;
import com.carret.market.application.member.dto.MemberRegisterRequest;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MemberServiceTest extends IntegrationTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    Member 회원;

    @BeforeEach
    void setup() {
        회원 =  회원_추가("test@naver.com");
    }

    @DisplayName("회원이 존재 하는 조회합니다.")
    @Test
    void findByEmail() {
        Member member = memberService.findByEmail("test@naver.com")
            .orElseThrow(() -> new MemberNotFoundException(ErrorCode.NOT_FOUND_MEMBER));

        assertThat(member).isEqualTo(회원);
    }

    @DisplayName("회원을 정보를 수정합니다.")
    @Test
    void changeMemberInfo() throws IOException {
        MemberChange memberChangeDto = createMemberChangeDto("수정");

        memberService.changeMemberInfo("test@naver.com", memberChangeDto);

        Member member = memberService.findByEmail("test@naver.com")
            .orElseThrow(() -> new MemberNotFoundException(ErrorCode.NOT_FOUND_MEMBER));
        assertThat(member.getNickname()).isEqualTo("수정");
    }

    private MemberChange createMemberChangeDto(String nickname) {
        return new MemberChange(nickname, "", null);
    }

    private Member 회원_추가(String email) {
        return memberRepository.save(Member.builder()
            .email(email)
            .password("password")
            .name("테스트")
            .nickname("닉네임")
            .role(Roletype.ROLE_MEMBER)
            .build());
    }

}
