package com.carret.market.service.member;

import com.carret.market.domain.member.Member;
import com.carret.market.domain.member.MemberRepository;
import com.carret.market.file.FileService;
import com.carret.market.file.UploadFile;
import com.carret.market.web.register.dto.MemberRegisterDto;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final FileService fileService;
    private final PasswordEncoder passwordEncoder;

    public void save(MemberRegisterDto memberRegisterDto) throws IOException {
        UploadFile uploadFile = fileService.storeFile(memberRegisterDto.getPreviewUrl());

        memberRepository.save(
            Member.of(memberRegisterDto.getEmail(),
                passwordEncoder.encode(memberRegisterDto.getPassword()),
                memberRegisterDto.getName(), memberRegisterDto.getNickname(),
                Objects.isNull(uploadFile) ? null : uploadFile.getStoreFileName()));
    }

    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }


}
