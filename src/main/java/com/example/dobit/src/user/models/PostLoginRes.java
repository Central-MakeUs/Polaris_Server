package com.example.dobit.src.user.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostLoginRes {
    private final int userIdx;
    private final String nickname;
    private final String jwt;
}
