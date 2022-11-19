package com.carret.market.web.member;

import com.carret.market.application.chat.ChatService;
import com.carret.market.application.chat.dto.PayFormResponse;
import com.carret.market.application.member.MemberService;
import com.carret.market.application.member.dto.SendPointRequest;
import com.carret.market.domain.member.Member;
import com.carret.market.support.authorization.AuthenticationPrincipal;
import com.carret.market.support.user.UserDetail;
import com.carret.market.application.member.dto.PointRequest;
import com.carret.market.application.member.dto.PointResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class PointController {

    private static final String POINT_SUCCESS_MESSAGE = "정상적으로 포인트가 적립되었습니다.";

    private final MemberService memberService;
    private final ChatService chatService;

    @GetMapping("/point")
    public String pointForm(@AuthenticationPrincipal UserDetail userDetail,
                            Model model) {

        model.addAttribute("memberPointInfo", memberService.findPoint(userDetail.getMemberDetail().getId()));
        model.addAttribute("roomInfo", new PayFormResponse());

        return "member/pointPage";
    }

    @PostMapping("/point")
    public ResponseEntity<PointResponse> pointCharge(@RequestBody PointRequest pointRequest,
                                                    @AuthenticationPrincipal UserDetail userDetail) {

        memberService.pointCharge(pointRequest, userDetail.getMemberDetail().getId());

        return ResponseEntity.ok(new PointResponse(true, POINT_SUCCESS_MESSAGE));
    }

    @GetMapping("/point/send")
    public String sendForm(@RequestParam Long roomId,
                            @AuthenticationPrincipal UserDetail userDetail,
                            Model model) {

        model.addAttribute("memberPointInfo", memberService.findPoint(userDetail.getMemberDetail().getId()));
        model.addAttribute("roomInfo", chatService.findPayFormResponse(roomId));

        return "member/pointPage";
    }

    @PostMapping("/point/send")
    public ResponseEntity<PointResponse> sendPoint(@RequestBody SendPointRequest sendPointRequest,
                                                    @AuthenticationPrincipal UserDetail userDetail) {

        memberService.sendPoint(sendPointRequest, userDetail.getMemberDetail().getId());

        return ResponseEntity.ok( new PointResponse(true, "성공적으로 전송되었습니다."));
    }

}
