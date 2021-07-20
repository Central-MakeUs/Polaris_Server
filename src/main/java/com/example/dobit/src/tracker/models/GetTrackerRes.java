package com.example.dobit.src.tracker.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GetTrackerRes {
    private final int userIdentityIdx;
    private final List checkDateList;
    private final List graphDataList;

}
