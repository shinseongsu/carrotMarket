package com.carret.market.web.member;

import static com.carret.market.global.exception.ErrorCode.NOT_FOUND_MEMBER;

import com.carret.market.application.chat.ChatService;
import com.carret.market.application.member.MemberService;
import com.carret.market.domain.member.Member;
import com.carret.market.global.exception.MemberNotFoundException;
import com.carret.market.support.authorization.AuthenticationPrincipal;
import com.carret.market.support.user.UserDetail;
import com.carret.market.application.member.dto.ChangeStatus;
import com.carret.market.application.member.dto.MemberChange;
import com.carret.market.application.member.dto.MemberInfo;
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

        model.addAttribute("memberPointInfo", memberService.findPoint(userDetail.getMemberDetail().getId()));
        model.addAttribute("memberInfoDto", memberService.findMyPage(userDetail.getMemberDetail().getEmail()));
        return "member/myPage";
    }

    @GetMapping("/myInfo")
    public String myInfoForm(@AuthenticationPrincipal UserDetail userDetail,
                            Model model) {

        Member member = memberService.findByEmail(userDetail.getMemberDetail().getEmail())
                .orElseThrow(() -> new MemberNotFoundException(NOT_FOUND_MEMBER));

        model.addAttribute("memberInfo", MemberInfo.of(member));
        return "member/myInfo";
    }

    @GetMapping("/subscript")
    public String subscriptListForm(@AuthenticationPrincipal UserDetail userDetail,
                                    Model model) {

        model.addAttribute("subscriptItems", memberService.findBySubscripts(userDetail.getMemberDetail().getId()));

        return "member/subscript";
    }

    @GetMapping("/itemMenu")
    public String itemMenuForm(@AuthenticationPrincipal UserDetail userDetail,
                                Model model) {

        model.addAttribute("myitemList", memberService.findMyItemList(userDetail.getMemberDetail().getId()));

        return "member/myItem";
    }

    @PostMapping("/myInfo/changeInfo")
    public ResponseEntity<ChangeStatus> changeInfoFrom(@AuthenticationPrincipal UserDetail userDetail,
                                                          MemberChange memberChangeDto) throws IOException {

        Member member = (Member) userDetail.getMemberDetail();
        memberService.changeMemberInfo(member.getEmail(), memberChangeDto);

        return ResponseEntity.ok().body(ChangeStatus.ok());
    }


}
