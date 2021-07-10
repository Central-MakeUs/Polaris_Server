package com.example.dobit.src.user.models;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class PatchPasswordReq {
    private String email;
    private String password;
    private String confirmPassword;
}
