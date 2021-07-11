package com.example.dobit.src.dontHabit.models;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class PatchIdentityDontHabitReq {
    private String dontName;
    private String dontAdvantage;
    private String dontEnv;
    private List<String> dontRoutine;
    private List<String> dontMotive;
    private List<String> dontElse;

}
