package com.example.dobit.src.habit.models;

import lombok.AllArgsConstructor;
import lombok.Getter;



@Getter
@AllArgsConstructor
public class GetHabitRes {
    private final int identityIdx;
    private final Integer doHabitIdx;
    private final String doHabitName;
    private final String doHabitSummary;
    private final String isDoHabitCheck;
    private final Integer dontHabitIdx;
    private final String dontHabitName;
    private final String dontHabitSummary;
    private final String isDontHabitCheck;
}