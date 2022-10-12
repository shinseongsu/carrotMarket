package com.carret.market.support.user;

import com.carret.market.domain.member.Roletype;

public interface MemberDetail {

    String getEmail();

    String getPassword();

    String getName();

    String getNickname();

    Roletype getRole();

    String getPreviewUrl();

    String getGeolocation();

}
