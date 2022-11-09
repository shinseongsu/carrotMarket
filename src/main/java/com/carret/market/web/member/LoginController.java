package com.carret.market.web.member;

import static com.carret.market.global.exception.ErrorCode.FAIL_LOGIN;

import java.util.Objects;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginView(Model model,
                            @RequestParam(required = false) String error) {

        if (Objects.nonNull(error)) {
            model.addAttribute("errorMessage", FAIL_LOGIN.getMessage());
        }

        return "member/login";
    }

}
