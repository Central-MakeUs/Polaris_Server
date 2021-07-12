package com.example.dobit.src.dontHabit;

import com.example.dobit.config.BaseException;
import com.example.dobit.src.dontElse.DontElseRepository;
import com.example.dobit.src.dontElse.models.DontElse;
import com.example.dobit.src.dontHabit.models.DontHabit;
import com.example.dobit.src.dontHabit.models.GetIdentityDontHabitRes;
import com.example.dobit.src.dontMotive.DontMotiveRepository;
import com.example.dobit.src.dontMotive.models.DontMotive;
import com.example.dobit.src.dontRoutine.DontRoutineRepository;
import com.example.dobit.src.dontRoutine.models.DontRoutine;
import com.example.dobit.src.userIdentity.models.UserIdentity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.dobit.config.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class DontHabitProvider {
    private final DontHabitRepository dontHabitRepository;
    private final DontElseRepository dontElseRepository;
    private final DontRoutineRepository dontRoutineRepository;
    private final DontMotiveRepository dontMotiveRepository;

    /**
     * userIdentity로 DontHabit 조회
     * @param userIdentity
     * @return existDontHabit
     * @throws BaseException
     */
    public Boolean retrieveExistingDontHabitByUserIdentity(UserIdentity userIdentity) throws BaseException {
        Boolean existDontHabit;
        try {
            existDontHabit = dontHabitRepository.existsByUserIdentityAndStatus(userIdentity,"ACTIVE");
        } catch (Exception ignored) {
            throw new BaseException(FAILED_TO_EXIST_BY_USERIDENTITY_AND_STATUS);
        }

        return existDontHabit;
    }

    /**
     * Idx로 DontHabit 조회
     * @param dnhIdx
     * @return DontHabit
     * @throws BaseException
     */
    public DontHabit retrieveDontHabitByDnhIdx(Integer dnhIdx) throws BaseException {
        DontHabit dontHabit;
        try {
            dontHabit = dontHabitRepository.findByDnhIdxAndStatus(dnhIdx,"ACTIVE");
        } catch (Exception ignored) {
            throw new BaseException(FAILED_TO_FIND_BY_DNHIDX_AND_STATUS);
        }


        return dontHabit;
    }

    /**
     * 정체성별 Dont 습관 조회하기 API
     * @param dontHabit
     * @return GetIdentityDontHabitRes
     * @throws BaseException
     */
    public GetIdentityDontHabitRes retrieveIdentityDontHabit(DontHabit dontHabit) throws BaseException {
        Integer dnhIdx = dontHabit.getDnhIdx();
        String dontName = dontHabit.getDnhName();
        String dontAdvantage = dontHabit.getDnhAdvantage();
        String dontEnv = dontHabit.getDnhEnv();
        List<String> dontRoutineList = new ArrayList<>();
        List<String> dontMotiveList = new ArrayList<>();
        List<String> dontElseList = new ArrayList<>();



        List<DontRoutine> dontRoutines;
        try {
            dontRoutines = dontRoutineRepository.findByDontHabitAndStatus(dontHabit,"ACTIVE");
        }catch (Exception e){
            throw new BaseException(FAILED_TO_FIND_BY_DONTHABIT_AND_STATUS);
        }

        for(int i=0;i<dontRoutines.size();i++){
            dontRoutineList.add(dontRoutines.get(i).getDnRoutineContent());
        }



        List<DontMotive> dontMotives;
        try {
            dontMotives = dontMotiveRepository.findByDontHabitAndStatus(dontHabit,"ACTIVE");
        }catch (Exception e){
            throw new BaseException(FAILED_TO_FIND_BY_DONTHABIT_AND_STATUS);
        }

        for(int i=0;i<dontMotives.size();i++){
            dontMotiveList.add(dontMotives.get(i).getDnMotiveContent());
        }


        List<DontElse> dontElses;
        try {
            dontElses = dontElseRepository.findByDontHabitAndStatus(dontHabit,"ACTIVE");
        }catch (Exception e){
            throw new BaseException(FAILED_TO_FIND_BY_DONTHABIT_AND_STATUS);
        }

        for(int i=0;i<dontElses.size();i++){
            dontElseList.add(dontElses.get(i).getDnElseContent());
        }



        return new GetIdentityDontHabitRes(dnhIdx,dontName,dontAdvantage,dontEnv,dontRoutineList,dontMotiveList,dontElseList);
    }


}
