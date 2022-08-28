package com.carret.market.web.member.dto;

import java.util.Objects;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class MemberRegisterDto {

    @NotBlank(message = "이메일은 필수 입력값입니다.")
    private String email;

    @NotBlank(message = "이름은 필수 입력값입니다.")
    private String name;

    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    private String nickname;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    private String password;

    private String passwordConfirm;
    private MultipartFile previewUrl;
    private String location;

    @Builder
    public MemberRegisterDto(String email, String name, String nickname, String password,
        String passwordConfirm, MultipartFile previewUrl, String location) {
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.previewUrl = previewUrl;
        this.location = location;
    }

    public boolean isEqualsPassword() {
        return Objects.equals(password, passwordConfirm);
    }

}
