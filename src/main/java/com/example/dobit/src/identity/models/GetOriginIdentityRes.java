package com.example.dobit.src.identity.models;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class GetOriginIdentityRes {
    private final int identityIdx;
    private final String identityName;
}