package com.example.dobit.src.dontHabit.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;


@AllArgsConstructor
@Getter
public class GetIdentityDontHabitRes {
    private final Integer dnhIdx;
    private final String dontName;
    private final String dontAdvantage;
    private final String dontEnv;
    private final List<String> dontRoutine;
    private final List<String> dontMotive;
    private final List<String> dontElse;

}

