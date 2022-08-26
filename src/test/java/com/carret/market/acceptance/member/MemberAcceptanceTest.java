package com.carret.market.acceptance.member;

import static com.carret.market.acceptance.member.MemberStep.로그인_후_정보_수정;
import static com.carret.market.acceptance.member.MemberStep.회원_저장;
import static org.assertj.core.api.Assertions.assertThat;

import com.carret.market.acceptance.AcceptanceTest;
import com.carret.market.domain.member.MemberRepository;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

@DisplayName("회원 관련 테스트")
public class MemberAcceptanceTest extends AcceptanceTest {

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("회원 저장")
    @Test
    void register() {
        ExtractableResponse<Response> response = 회원_저장("test@naver.com");

        assertThat(response.statusCode()).isEqualTo(HttpStatus.FOUND.value());
        assertThat(memberRepository.findAll()).hasSize(1);
    }

    @DisplayName("회원정보를 수정 합니다.")
    @Test
    void infoUpdate() {
        회원_저장("test@naver.com");

        로그인_후_정보_수정("test@naver.com", "수정된 이메일");

        assertThat(memberRepository.findByEmail("test@naver.com").get().getNickname()).isEqualTo("수정된 이메일");
    }


}
