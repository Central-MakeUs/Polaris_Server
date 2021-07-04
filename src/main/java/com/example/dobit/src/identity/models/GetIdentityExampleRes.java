package com.example.dobit.src.identity.models;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class GetIdentityExampleRes {
    private final int identityIdx;
    private final String identityName;
}