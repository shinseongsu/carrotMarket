package com.carret.market.web.member;

import com.carret.market.application.member.MemberService;
import com.carret.market.web.member.dto.MemberRegisterDto;
import com.carret.market.web.member.validate.RegisterValidate;
import java.io.IOException;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterController {

    private final MemberService memberService;
    private final RegisterValidate registerValidate;

    @InitBinder
    public void registerBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(registerValidate);
    }

    @GetMapping
    public String registerView(Model model) {
        model.addAttribute("memberRegisterDto", new MemberRegisterDto());

        return "member/register";
    }

    @PostMapping
    public String register(@Valid @ModelAttribute MemberRegisterDto memberRegisterDto,
        BindingResult errors) throws IOException {

        if(errors.hasErrors()) {
            return "member/register";
        }

        memberService.save(memberRegisterDto);

        return "redirect:/login";
    }

}
