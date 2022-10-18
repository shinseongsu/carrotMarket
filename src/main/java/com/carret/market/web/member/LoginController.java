package com.carret.market.web.member;

import java.util.Objects;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginView(Model model, @RequestParam(required = false) String error) {
        if(Objects.nonNull(error)) {
            model.addAttribute("errorMessage", "로그인 실패하였습니다.");
        }

        return "member/login";
    }

}
