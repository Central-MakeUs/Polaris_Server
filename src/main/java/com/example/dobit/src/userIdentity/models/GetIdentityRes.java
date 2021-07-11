package com.example.dobit.src.userIdentity.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetIdentityRes {
    private final Integer userIdentityIdx;
    private final String userIdentityName;
    private final Integer userIdentityColorIdx;
    private final String userIdentityColorName;
    private final String doHabit;
    private final String doNotHabit;
}
