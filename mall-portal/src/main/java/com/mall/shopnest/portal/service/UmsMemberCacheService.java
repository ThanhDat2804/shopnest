package com.mall.shopnest.portal.service;

import com.mall.shopnest.core.model.ums.UmsMember;

public interface UmsMemberCacheService {

    void delMember(Long memberId);

    UmsMember getMember(String username);

    void setMember(UmsMember member);

    void setAuthCode(String telephone, String authCode);

    String getAuthCode(String telephone);
}
