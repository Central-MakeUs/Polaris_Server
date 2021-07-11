
package com.example.dobit.src.doHabit.models;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class PatchIdentityDoHabitReq {
    private String doName;
    private String doWhen;
    private String doWhere;
    private String doStart;
    private List<String> doRoutine;
    private List<String> doEnv;
    private List<String> doNext;
    private List<String> doElse;


}