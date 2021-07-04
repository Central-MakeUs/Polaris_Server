package com.example.dobit.src.userToIdentity.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetIdentityRes {
    private final Integer userToIdentityIdx;
    private final String identityName;
    private final String doHabit;
    private final String doNotHabit;
}
