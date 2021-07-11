package com.example.dobit.src.doHabit;

import com.example.dobit.config.BaseException;
import com.example.dobit.src.doElse.DoElseRepository;
import com.example.dobit.src.doElse.models.DoElse;
import com.example.dobit.src.doEnv.DoEnvRepository;
import com.example.dobit.src.doEnv.models.DoEnv;
import com.example.dobit.src.doHabit.models.DoHabit;
import com.example.dobit.src.doHabit.models.GetIdentityDoHabitRes;
import com.example.dobit.src.doNext.DoNextRepository;
import com.example.dobit.src.doNext.models.DoNext;
import com.example.dobit.src.doRoutine.DoRoutineRepository;
import com.example.dobit.src.doRoutine.models.DoRoutine;
import com.example.dobit.src.user.models.UserInfo;
import com.example.dobit.src.userIdentity.models.UserIdentity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.dobit.config.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class DoHabitProvider {
    private final DoHabitRepository doHabitRepository;
    private final DoRoutineRepository doRoutineRepository;
    private final DoEnvRepository doEnvRepository;
    private final DoNextRepository doNextRepository;
    private final DoElseRepository doElseRepository;



    /**
     * Idx로 DoHabit 조회
     * @param dhIdx
     * @return DoHabit
     * @throws BaseException
     */
    public DoHabit retrieveDoHabitByDhIdx(Integer dhIdx) throws BaseException {
        DoHabit doHabit;
        try {
            doHabit = doHabitRepository.findByDhIdxAndStatus(dhIdx,"ACTIVE");
        } catch (Exception ignored) {
            throw new BaseException(FAILED_TO_FIND_BY_DHIDX_AND_STATUS);
        }


        return doHabit;
    }

    /**
     * userInfo로 DoHabit 조회
     * @param userIdentity
     * @return existDoHabit
     * @throws BaseException
     */
    public Boolean retrieveExistingDoHabitByUserIdentity(UserIdentity userIdentity) throws BaseException {
        Boolean existDoHabit;
        try {
            existDoHabit = doHabitRepository.existsByUserIdentityAndStatus(userIdentity,"ACTIVE");
        } catch (Exception ignored) {
            throw new BaseException(FAILED_TO_EXIST_BY_USERINFO_AND_STATUS);
        }

        return existDoHabit;
    }

    /**
     * 정체성별 Do 습관 조회하기 API
     * @param doHabit
     * @return GetIdentityDoHabitRes
     * @throws BaseException
     */
    public GetIdentityDoHabitRes retrieveIdentityDoHabit(DoHabit doHabit) throws BaseException {

        String doName = doHabit.getDhName();
        String doWhen = doHabit.getDhWhen();
        String doWhere = doHabit.getDhWhere();
        String doStart = doHabit.getDhStart();
        List<String> doRoutineList = new ArrayList<>();
        List<String> doEnvList = new ArrayList<>();
        List<String> doNextList = new ArrayList<>();
        List<String> doElseList = new ArrayList<>();


        List<DoRoutine> doRoutines;
        try {
            doRoutines = doRoutineRepository.findByDoHabitAndStatus(doHabit,"ACTIVE");
        }catch (Exception e){
            throw new BaseException(FAILED_TO_FIND_BY_DOHABIT_AND_STATUS);
        }

        for(int i=0;i<doRoutines.size();i++){
            doRoutineList.add(doRoutines.get(i).getDRoutineContent());
        }



        List<DoEnv> doEnvs;
        try {
            doEnvs = doEnvRepository.findByDoHabitAndStatus(doHabit,"ACTIVE");
        }catch (Exception e){
            throw new BaseException(FAILED_TO_FIND_BY_DOHABIT_AND_STATUS);
        }

        for(int i=0;i<doEnvs.size();i++){
            doEnvList.add(doEnvs.get(i).getDEnvContent());
        }

        List<DoNext> doNexts;
        try {
            doNexts = doNextRepository.findByDoHabitAndStatus(doHabit,"ACTIVE");
        }catch (Exception e){
            throw new BaseException(FAILED_TO_FIND_BY_DOHABIT_AND_STATUS);
        }

        for(int i=0;i<doNexts.size();i++){
            doNextList.add(doNexts.get(i).getDNextContent());
        }

        List<DoElse> doElses;
        try {
            doElses = doElseRepository.findByDoHabitAndStatus(doHabit,"ACTIVE");
        }catch (Exception e){
            throw new BaseException(FAILED_TO_FIND_BY_DOHABIT_AND_STATUS);
        }

        for(int i=0;i<doElses.size();i++){
            doElseList.add(doElses.get(i).getDElseContent());
        }

        return new GetIdentityDoHabitRes(doHabit.getDhIdx(),doName,doWhen,doWhere,doStart,doRoutineList,doEnvList,doNextList,doElseList);
    }
}
