package com.example.dobit.src.userIdentity.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetIdentityRes {
    private final int userIdentityIdx;
    private final String userIdentityName;
    private final Integer doHabitIdx;
    private final String doHabitName;
    private final Integer dontHabitIdx;
    private final String dontHabitName;
    private final int userIdentityColorIdx;
    private final String userIdentityColorName;
}
