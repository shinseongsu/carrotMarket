package com.carret.market.application.member;

import static com.carret.market.global.exception.ErrorCode.NOT_FOUND_MEMBER;

import com.carret.market.domain.like.LikesRepository;
import com.carret.market.domain.member.Member;
import com.carret.market.domain.member.MemberRepository;
import com.carret.market.domain.member.Point;
import com.carret.market.domain.member.PointRepository;
import com.carret.market.domain.member.Roletype;
import com.carret.market.global.exception.MemberNotFoundException;
import com.carret.market.infrastructure.file.S3UploadUtils;
import com.carret.market.infrastructure.file.UploadFile;
import com.carret.market.support.user.UserDetailService;
import com.carret.market.application.member.dto.MemberChange;
import com.carret.market.application.member.dto.MemberInfo;
import com.carret.market.application.member.dto.MemberPointResponse;
import com.carret.market.application.member.dto.MemberRegisterRequest;
import com.carret.market.application.member.dto.MyItemInfo;
import com.carret.market.application.member.dto.PointRequest;
import com.carret.market.application.member.dto.SubscriptResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    public void save(MemberRegisterRequest memberRegisterRequest) {
        UploadFile uploadFile = fileService.uploadFile(memberRegisterRequest.getPreviewUrl());

        memberRepository.save(
            Member.builder()
                .email(memberRegisterRequest.getEmail())
                .password(passwordEncoder.encode(memberRegisterRequest.getPassword()))
                .name(memberRegisterRequest.getName())
                .nickname(memberRegisterRequest.getNickname())
                .role(Roletype.ROLE_MEMBER)
                .previewUrl(Objects.isNull(uploadFile) ? null : uploadFile.getFileUploadUrl())
                .joinedAt(LocalDateTime.now())
                .geolocation(memberRegisterRequest.getLocation())
                .build());
    }

    @Transactional(readOnly = true)
    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public void changeMemberInfo(String email, MemberChange memberChangeDto) throws IOException {
        Member member = memberRepository.findByEmail(email)
            .orElseThrow(() -> new MemberNotFoundException(NOT_FOUND_MEMBER));

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
    public List<MyItemInfo> findMyItemList(Long memberId) {
        return memberRepository.findMyItemInfoByMemberId(memberId);
    }


    @Transactional(readOnly = true)
    public MemberInfo findMyPage(String email) {
        Member member = memberRepository.findByEmail(email)
            .orElseThrow(() -> new MemberNotFoundException(NOT_FOUND_MEMBER));

        return MemberInfo.of(member);
    }

    @Transactional(readOnly = true)
    public MemberPointResponse findPoint(Long memberId) {
        return memberRepository.findPointByMemberId(memberId);
    }

    public void pointCharge(PointRequest pointRequest, Long memberId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new MemberNotFoundException(NOT_FOUND_MEMBER));

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
