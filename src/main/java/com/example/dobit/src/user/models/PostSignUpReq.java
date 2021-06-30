package com.example.dobit.src.user.models;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PUBLIC) // Unit Test 를 위해 PUBLIC
@Getter
public class PostSignUpReq {
    private String email;
    private String password;
    private String nickname;
}