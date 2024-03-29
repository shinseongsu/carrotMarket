package com.carret.market.domain.member;

import com.carret.market.application.member.dto.MemberPointResponse;
import com.carret.market.application.member.dto.MyItemInfo;
import java.util.List;

public interface MemberRepositoryCustom {

    List<MyItemInfo> findMyItemInfoByMemberId(Long memberId);
    
    MemberPointResponse findPointByMemberId(Long memberId);
    
}
