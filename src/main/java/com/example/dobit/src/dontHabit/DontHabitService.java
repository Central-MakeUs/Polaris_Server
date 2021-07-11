package com.example.dobit.src.dontHabit;

import com.example.dobit.config.BaseException;
import com.example.dobit.src.doElse.models.DoElse;
import com.example.dobit.src.doEnv.models.DoEnv;
import com.example.dobit.src.doHabit.models.DoHabit;
import com.example.dobit.src.doHabit.models.PostIdentityDoHabitReq;
import com.example.dobit.src.doNext.models.DoNext;
import com.example.dobit.src.doRoutine.models.DoRoutine;
import com.example.dobit.src.dontElse.DontElseRepository;
import com.example.dobit.src.dontElse.models.DontElse;
import com.example.dobit.src.dontHabit.models.DontHabit;
import com.example.dobit.src.dontHabit.models.PostIdentityDontHabitReq;
import com.example.dobit.src.dontMotive.DontMotiveRepository;
import com.example.dobit.src.dontMotive.models.DontMotive;
import com.example.dobit.src.dontRoutine.DontRoutineRepository;
import com.example.dobit.src.dontRoutine.models.DontRoutine;
import com.example.dobit.src.userIdentity.models.UserIdentity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.dobit.config.BaseResponseStatus.*;
@Service
@RequiredArgsConstructor
public class DontHabitService {
    private final DontHabitRepository dontHabitRepository;
    private final DontHabitProvider dontHabitProvider;
    private final DontRoutineRepository dontRoutineRepository;
    private final DontMotiveRepository dontMotiveRepository;
    private final DontElseRepository dontElseRepository;

    /**
     * 정체성별 Dont 습관 입력하기 API
     * @param userIdentity, postIdentityDontHabitReq
     * @return void
     * @throws BaseException
     */
    @Transactional
    public void  createIdentityDontHabit(UserIdentity userIdentity, PostIdentityDontHabitReq postIdentityDontHabitReq) throws BaseException {
        String dontName = postIdentityDontHabitReq.getDontName();
        String dontAdvantage = postIdentityDontHabitReq.getDontAdvantage();
        String dontEnv = postIdentityDontHabitReq.getDontEnv();
        List<String> dontRoutineList = postIdentityDontHabitReq.getDontRoutine();
        List<String> dontMotiveList = postIdentityDontHabitReq.getDontMotive();
        List<String> dontElseList = postIdentityDontHabitReq.getDontElse();


        DontHabit dnh = new DontHabit(dontName,dontAdvantage,dontEnv,userIdentity);
        Integer newDnhIdx;
        try{
            dontHabitRepository.save(dnh);
            newDnhIdx = dnh.getDnhIdx();
        }catch (Exception exception){
            throw new BaseException(FAILED_TO_SAVE_DONT_HABIT);
        }

        DontHabit dontHabit = dontHabitProvider.retrieveDontHabitByDnhIdx(newDnhIdx);

        if (dontRoutineList!=null){
            for(int i =0;i<dontRoutineList.size();i++) {
                String dnRoutineContent = dontRoutineList.get(i);
                DontRoutine dontRoutine = new DontRoutine(dnRoutineContent, dontHabit);
                try{
                    dontRoutineRepository.save(dontRoutine);
                }catch (Exception exception){
                    throw new BaseException(FAILED_TO_SAVE_DONT_ROUTINE);
                }
            }
        }

        if (dontMotiveList!=null){
            for(int i=0;i<dontMotiveList.size();i++){
                String dnMotiveContent = dontMotiveList.get(i);
                DontMotive dontMotive = new DontMotive(dnMotiveContent,dontHabit);
                try{
                    dontMotiveRepository.save(dontMotive);
                }catch(Exception exception) {
                    throw new BaseException(FAILED_TO_SAVE_DONT_MOTIVE);
                }
            }
        }
        if (dontElseList!=null){
            for(int i=0;i<dontElseList.size();i++){
                String dnElseContet = dontElseList.get(i);
                DontElse dontElse = new DontElse(dnElseContet,dontHabit);
                try {
                    dontElseRepository.save(dontElse);
                }catch (Exception e){
                    throw new BaseException(FAILED_TO_SAVE_DONT_ELSE);
                }
            }

        }


    }
}
