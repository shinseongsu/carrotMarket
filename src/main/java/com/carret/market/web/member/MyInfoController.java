package com.carret.market.web.member;

import com.carret.market.application.chat.ChatService;
import com.carret.market.application.member.MemberService;
import com.carret.market.domain.member.Member;
import com.carret.market.global.exception.MemberNotFoundException;
import com.carret.market.support.authorization.AuthenticationPrincipal;
import com.carret.market.support.user.UserDetail;
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

@Controller
@RequiredArgsConstructor
public class MyInfoController {

    private final MemberService memberService;
    private final ChatService chatService;

    @GetMapping("/myPage")
    public String myPageForm(@AuthenticationPrincipal UserDetail userDetail,
        Model model) {

        model.addAttribute("memberPointInfo", memberService.selectPoint(userDetail.getMemberDetail().getId()));
        model.addAttribute("memberInfoDto", memberService.selectMyPage(userDetail.getMemberDetail().getEmail()));
        return "/member/myPage";
    }

    @GetMapping("/myInfo")
    public String myInfoForm(@AuthenticationPrincipal UserDetail userDetail,
        Model model) {
        Member member = memberService.findByEmail(userDetail.getMemberDetail().getEmail())
                .orElseThrow(() -> new MemberNotFoundException("회원이 존재하지 않습니다."));

        model.addAttribute("memberInfoDto", MemberInfoDto.of(member));
        return "/member/myInfo";
    }

    @GetMapping("/subscript")
    public String subscriptListForm(@AuthenticationPrincipal UserDetail userDetail,
        Model model) {

        model.addAttribute("subscriptItems", memberService.findBySubscripts(userDetail.getMemberDetail().getId()));

        return "/member/subscript";
    }

    @GetMapping("/chatmenu")
    public String chatmenuForm(@AuthenticationPrincipal UserDetail userDetail,  Model model) {

        model.addAttribute("roomList", chatService.findByRoomList(userDetail.getMemberDetail().getId())) ;

        return "/member/roomList";
    }

    @GetMapping("/itemMenu")
    public String itemMenuForm(@AuthenticationPrincipal UserDetail userDetail, Model model) {

        model.addAttribute("myitemList", memberService.selectMyItemList(userDetail.getMemberDetail().getId()));

        return "/member/myItem";
    }

    @PostMapping("/myInfo/changeInfo")
    public ResponseEntity<ChangeStatusDto> changeInfoFrom(@AuthenticationPrincipal UserDetail userDetail,
                                                          MemberChangeDto memberChangeDto) throws IOException {
        Member member = (Member) userDetail.getMemberDetail();
        memberService.changeMemberInfo(member.getEmail(), memberChangeDto);

        return ResponseEntity.ok().body(ChangeStatusDto.ok());
    }


}
