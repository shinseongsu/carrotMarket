package com.carret.market.domain.like;

import com.carret.market.application.member.dto.SubscriptResponse;
import java.util.List;

public interface LikesRepositoryCustom {

    List<SubscriptResponse> findBySubscripts(Long memberId);
}
