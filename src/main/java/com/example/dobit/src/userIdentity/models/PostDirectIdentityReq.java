package com.example.dobit.src.userIdentity.models;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class PostDirectIdentityReq {
    private String identityName;
}

