package com.example.dobit.src.dontHabitCheck.models;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class PostDontHabitCheckReq {
    private Integer dontHabitIdx;
}
