package com.carret.market.web.register.validate;

import com.carret.market.domain.member.Member;
import com.carret.market.service.member.MemberService;
import com.carret.market.web.register.dto.MemberRegisterDto;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class RegisterValidate implements Validator {
    private final MemberService memberService;

    @Override
    public boolean supports(Class<?> clazz) {
        return MemberRegisterDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if(target instanceof MemberRegisterDto && !errors.hasErrors()) {
            MemberRegisterDto memberRegisterDto = (MemberRegisterDto) target;

            isNotEqualsPassword(errors, memberRegisterDto);
            isExsitsEmail(errors, memberRegisterDto);
        }
    }

    private void isNotEqualsPassword(Errors errors, MemberRegisterDto memberRegisterDto) {
        if(!memberRegisterDto.isEqualsPassword()) {
            errors.rejectValue("passwordConfirm", "", "비밀번호가 다릅니다.");
            return;
        }
    }

    private void isExsitsEmail(Errors errors, MemberRegisterDto memberRegisterDto) {
        Optional<Member> optionalMember = memberService.findByEmail(memberRegisterDto.getEmail());

        if(optionalMember.isPresent()) {
            errors.rejectValue("existsEmail", "", "이미 존재하는 이메일입니다.");
            return;
        }
    }

}
