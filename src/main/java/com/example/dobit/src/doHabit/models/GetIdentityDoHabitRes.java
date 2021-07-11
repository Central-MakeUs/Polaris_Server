package com.example.dobit.src.doHabit.models;


import lombok.AllArgsConstructor;
import lombok.Getter;


import java.util.List;


@AllArgsConstructor
@Getter
public class GetIdentityDoHabitRes {
    private final Integer dhIdx;
    private final String doName;
    private final String doWhen;
    private final String doWhere;
    private final String doStart;
    private final List<String> doRoutine;
    private final List<String> doEnv;
    private final List<String> doNext;
    private final List<String> doElse;


}

