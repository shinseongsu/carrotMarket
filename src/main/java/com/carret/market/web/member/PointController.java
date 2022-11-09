package com.carret.market.web.member;

import com.carret.market.application.member.MemberService;
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

@Controller
@RequiredArgsConstructor
public class PointController {

    private static final String POINT_SUCCESS_MESSAGE = "정상적으로 포인트가 적립되었습니다.";

    private final MemberService memberService;

    @GetMapping("/point")
    public String pointForm(@AuthenticationPrincipal UserDetail userDetail,
                            Model model) {

        model.addAttribute("memberPointInfo", memberService.findPoint(userDetail.getMemberDetail().getId()));

        return "member/pointPage";
    }

    @PostMapping("/point")
    public ResponseEntity<PointResponse> pointCharge(@RequestBody PointRequest pointRequest,
                                                    @AuthenticationPrincipal UserDetail userDetail) {

        memberService.pointCharge(pointRequest, userDetail.getMemberDetail().getId());

        return ResponseEntity.ok(new PointResponse(true, POINT_SUCCESS_MESSAGE));
    }

}
