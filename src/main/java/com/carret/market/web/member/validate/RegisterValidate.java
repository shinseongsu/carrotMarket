package com.carret.market.web.member.validate;

import com.carret.market.domain.member.Member;
import com.carret.market.application.member.MemberService;
import com.carret.market.application.member.dto.MemberRegisterRequest;
import java.util.Objects;
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
        return MemberRegisterRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if(target instanceof MemberRegisterRequest && !errors.hasErrors()) {
            MemberRegisterRequest memberRegisterRequest = (MemberRegisterRequest) target;

            isNotEqualsPassword(errors, memberRegisterRequest);
            isExisisLocation(errors, memberRegisterRequest);
            isExsitsEmail(errors, memberRegisterRequest);
        }
    }

    private void isNotEqualsPassword(Errors errors, MemberRegisterRequest memberRegisterRequest) {
        if(!memberRegisterRequest.isEqualsPassword()) {
            errors.rejectValue("passwordConfirm", "", "비밀번호가 다릅니다.");
            return;
        }
    }

    private void isExsitsEmail(Errors errors, MemberRegisterRequest memberRegisterRequest) {
        Optional<Member> optionalMember = memberService.findByEmail(memberRegisterRequest.getEmail());

        if(optionalMember.isPresent()) {
            errors.reject("existsEmail", "이미 존재하는 이메일입니다.");
            return;
        }
    }

    private  void isExisisLocation(Errors errors, MemberRegisterRequest memberRegisterRequest) {
        if(Objects.isNull(memberRegisterRequest.getLocation())) {
            errors.reject("existLocation", "위치 정보가 켜져 있어야 회원 가입 가능합니다.");
            return;
        }
    }

}
