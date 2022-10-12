package com.carret.market.web.member;

import com.carret.market.support.authorization.AuthenticationPrincipal;
import com.carret.market.support.user.UserDetail;
import com.carret.market.domain.member.Member;
import com.carret.market.global.exception.MemberNotFoundException;
import com.carret.market.application.member.MemberService;
import com.carret.market.web.member.dto.ChangeStatusDto;
import com.carret.market.web.member.dto.MemberChangeDto;
import com.carret.market.web.member.dto.MemberInfoDto;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/myInfo")
public class MyInfoController {

    private final MemberService memberService;

    @GetMapping
    public String myInfoForm(@AuthenticationPrincipal UserDetail userDetail,
        Model model) {
        Member member = memberService.findByEmail(userDetail.getMemberDetail().getEmail())
                .orElseThrow(() -> new MemberNotFoundException("회원이 존재하지 않습니다."));

        model.addAttribute("memberInfoDto", MemberInfoDto.of(member));
        return "/member/myInfo";
    }

    @PostMapping("/changeInfo")
    public ResponseEntity<ChangeStatusDto> changeInfoFrom(@AuthenticationPrincipal UserDetail userDetail,
                                                          MemberChangeDto memberChangeDto) throws IOException {
        Member member = (Member) userDetail.getMemberDetail();
        memberService.changeMemberInfo(member.getEmail(), memberChangeDto);

        return ResponseEntity.ok().body(ChangeStatusDto.ok());
    }


}
