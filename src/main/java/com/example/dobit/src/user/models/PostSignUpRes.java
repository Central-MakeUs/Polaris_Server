package com.example.dobit.src.user.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostSignUpRes {
    private final int userIdx;
    private final String jwt;
}
