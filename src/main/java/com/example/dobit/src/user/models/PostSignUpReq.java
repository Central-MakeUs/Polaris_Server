package com.example.dobit.src.user.models;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class PostSignUpReq {
    private String email;
    private String password;
    private String confirmPassword;
    private String nickname;
}
