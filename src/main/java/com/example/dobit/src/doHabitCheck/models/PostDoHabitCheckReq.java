package com.example.dobit.src.doHabitCheck.models;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class PostDoHabitCheckReq {
    private Integer doHabitIdx;
}
