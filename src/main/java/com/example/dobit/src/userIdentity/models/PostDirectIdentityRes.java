package com.example.dobit.src.userIdentity.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostDirectIdentityRes {
    private final int identityIdx;
    private final String identityName;
}
