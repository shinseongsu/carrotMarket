package com.carret.market.web.member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MemberController {

    @GetMapping("/login")
    public ModelAndView login(ModelAndView mav) {
        mav.setViewName("/member/login");

        return mav;
    }

    @GetMapping("/register")
    public ModelAndView logout(ModelAndView mav) {
        mav.setViewName("/member/register");
        return mav;
    }

}
