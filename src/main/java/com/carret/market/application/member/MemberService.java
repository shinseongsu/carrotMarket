package com.carret.market.application.member;

import com.carret.market.application.auth.AuthUserDetailsService;
import com.carret.market.domain.like.LikesRepository;
import com.carret.market.domain.member.Member;
import com.carret.market.domain.member.MemberRepository;
import com.carret.market.domain.member.Point;
import com.carret.market.domain.member.PointRepository;
import com.carret.market.domain.member.Roletype;
import com.carret.market.global.exception.MemberNotFoundException;
import com.carret.market.infrastructure.file.S3UploadUtils;
import com.carret.market.infrastructure.file.UploadFile;
import com.carret.market.support.user.MemberDetail;
import com.carret.market.support.user.UserDetail;
import com.carret.market.support.user.UserDetailService;
import com.carret.market.web.member.dto.MemberChangeDto;
import com.carret.market.web.member.dto.MemberInfoDto;
import com.carret.market.web.member.dto.MemberPointResponse;
import com.carret.market.web.member.dto.MemberRegisterDto;
import com.carret.market.web.member.dto.MyItemInfo;
import com.carret.market.web.member.dto.PointRequest;
import com.carret.market.web.member.dto.SubscriptResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final S3UploadUtils fileService;
    private final PasswordEncoder passwordEncoder;
    private final LikesRepository likesRepository;
    private final PointRepository pointRepository;
    private final UserDetailService userDetailService;

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

        if (isChangePreview(memberChangeDto.getPreviewUrl())) {
            UploadFile uploadFile = fileService.uploadFile(memberChangeDto.getPreviewUrl());
            member.changeProfile(uploadFile.getFileUploadUrl());
        }
        member.changeInfo(memberChangeDto.getNickname(), memberChangeDto.getLocation());

        userDetailService.memberInfoChange(member);
    }

    private boolean isChangePreview(MultipartFile multipartFile) {
        return ObjectUtils.isEmpty(multipartFile) || multipartFile.isEmpty() ? false : true;
    }

    @Transactional(readOnly = true)
    public List<SubscriptResponse> findBySubscripts(Long memberId) {
        return likesRepository.findBySubscripts(memberId);
    }

    @Transactional(readOnly = true)
    public List<MyItemInfo> selectMyItemList(Long memberId) {
        return memberRepository.findMyItemInfoByMemberId(memberId);
    }


    @Transactional(readOnly = true)
    public MemberInfoDto selectMyPage(String email) {
        Member member = memberRepository.findByEmail(email)
            .orElseThrow(() -> new MemberNotFoundException("회원이 존재하지 않습니다."));

        return MemberInfoDto.of(member);
    }

    @Transactional(readOnly = true)
    public MemberPointResponse selectPoint(Long memberId) {
        return memberRepository.findPointByMemberId(memberId);
    }

    public void pointCharge(PointRequest pointRequest, Long memberId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 회원입니다."));

        pointRepository.save(Point.builder()
            .description(pointRequest.getDescription())
            .amount(pointRequest.getAmount())
            .pgType(pointRequest.getPgType())
            .currency(pointRequest.getCurrency())
            .pgTid(pointRequest.getPgTid())
            .merchantId(pointRequest.getMerchatUid())
            .member(member)
            .build());

        member.charge(pointRequest.getAmount());
    }

}
