package com.carret.market.config.security.user;

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
