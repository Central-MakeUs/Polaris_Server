package com.example.dobit.src.userIdentity.models;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class PostIdentityReq {
    private List<Integer> identityList;
}

