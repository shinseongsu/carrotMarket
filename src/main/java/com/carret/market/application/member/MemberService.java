package com.carret.market.application.member;

import static com.carret.market.global.exception.ErrorCode.NOT_FOUND_MEMBER;

import com.carret.market.application.chat.dto.ChatDetail;
import com.carret.market.application.chat.dto.MessageRequest;
import com.carret.market.application.member.dto.PointResponse;
import com.carret.market.application.member.dto.SendPointRequest;
import com.carret.market.domain.chat.Message;
import com.carret.market.domain.chat.MessageRepository;
import com.carret.market.domain.chat.MessageStatus;
import com.carret.market.domain.chat.Room;
import com.carret.market.domain.chat.RoomRepository;
import com.carret.market.domain.like.LikesRepository;
import com.carret.market.domain.member.Currency;
import com.carret.market.domain.member.Member;
import com.carret.market.domain.member.MemberRepository;
import com.carret.market.domain.member.PgType;
import com.carret.market.domain.member.Point;
import com.carret.market.domain.member.PointRepository;
import com.carret.market.domain.member.Roletype;
import com.carret.market.global.exception.ErrorCode;
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
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationEventPublisher;
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

    private static final String SEND_MESSAGE = "%s님이 %d 포인트를 전송하였습니다.";

    private final MemberRepository memberRepository;
    private final S3UploadUtils fileService;
    private final PasswordEncoder passwordEncoder;
    private final LikesRepository likesRepository;
    private final PointRepository pointRepository;
    private final UserDetailService userDetailService;
    private final MessageRepository messageRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

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

    public void sendPoint(SendPointRequest sendPointRequest, Long memberId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new MemberNotFoundException(NOT_FOUND_MEMBER));

        Member sellerMember = memberRepository.findById(sendPointRequest.getSendId())
                .orElseThrow(() -> new MemberNotFoundException(NOT_FOUND_MEMBER));

        checkMinuPoint(member, sendPointRequest.getAmount());
        member.minusPoint(sendPointRequest.getAmount());
        sellerMember.charge(sendPointRequest.getAmount());

        pointRepository.save( new Point("포인트 전송", sendPointRequest.getAmount(), PgType.APPLE_MARKET_PAY, Currency.KRW, "SEND", "SEND", member ));
        pointRepository.save( new Point("포인트 받기", sendPointRequest.getAmount(), PgType.APPLE_MARKET_PAY, Currency.KRW, "GET", "GET", sellerMember ));
        applicationEventPublisher.publishEvent( new MessageRequest( String.format(SEND_MESSAGE, member.getNickname(), sendPointRequest.getAmount()), member.getId(), sendPointRequest.getRoomId(), MessageStatus.NOTICE ) );
    }

    private void checkMinuPoint(Member member, int amount) {
        if(member.getPoint() - amount < 0) {
            throw new IllegalArgumentException("잔액이 부족합니다.");
        }
    }

}
