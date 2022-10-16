package com.carret.market.application.member;

import com.carret.market.domain.like.LikesRepository;
import com.carret.market.domain.member.Member;
import com.carret.market.domain.member.MemberRepository;
import com.carret.market.domain.member.Roletype;
import com.carret.market.infrastructure.file.S3UploadUtils;
import com.carret.market.infrastructure.file.UploadFile;
import com.carret.market.global.exception.MemberNotFoundException;
import com.carret.market.web.member.dto.MemberChangeDto;
import com.carret.market.web.member.dto.MemberRegisterDto;
import com.carret.market.web.member.dto.MyItemInfo;
import com.carret.market.web.member.dto.SubscriptResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final S3UploadUtils fileService;
    private final PasswordEncoder passwordEncoder;
    private final LikesRepository likesRepository;

    private static final String EMPTY_MEMBER = "회웜이 존재하지 않습니다.";

    public void save(MemberRegisterDto memberRegisterDto) {
        UploadFile uploadFile = fileService.uploadFile(memberRegisterDto.getPreviewUrl());

        memberRepository.save(
            Member.builder()
                .email(memberRegisterDto.getEmail())
                .password(passwordEncoder.encode(memberRegisterDto.getPassword()))
                .name(memberRegisterDto.getName())
                .nickname(memberRegisterDto.getNickname())
                .role(Roletype.ROLE_MEMBER)
                .previewUrl(Objects.isNull(uploadFile) ? null : uploadFile.getFileUploadUrl())
                .joinedAt(LocalDateTime.now())
                .geolocation(memberRegisterDto.getLocation())
                .build());
    }

    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public void changeMemberInfo(String email, MemberChangeDto memberChangeDto) throws IOException {
        Member member = memberRepository.findByEmail(email)
            .orElseThrow(() -> new MemberNotFoundException(EMPTY_MEMBER));

        if(isChangePreview(memberChangeDto.getPreviewUrl())) {
            UploadFile uploadFile = fileService.uploadFile(memberChangeDto.getPreviewUrl());
            member.changeProfile(uploadFile.getStoreFileName());
        }

        member.changeInfo(memberChangeDto.getNickname(), memberChangeDto.getLocation());
    }

    private boolean isChangePreview(MultipartFile multipartFile) {
        return ObjectUtils.isEmpty(multipartFile) || multipartFile.isEmpty() ? false : true;
    }

    @Transactional(readOnly = true)
    public List<SubscriptResponse> findBySubscripts(Long memberId) {
        return likesRepository.findBySubscripts(memberId);
    }

    public List<MyItemInfo> selectMyItemList(Long memberId) {
        return memberRepository.findMyItemInfoByMemberId(memberId);
    }

}
