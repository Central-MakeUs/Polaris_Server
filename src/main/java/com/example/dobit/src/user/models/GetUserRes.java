package com.example.dobit.src.user.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetUserRes {
    private final int userIdx;
    private final String email;
    private final String nickname;
}