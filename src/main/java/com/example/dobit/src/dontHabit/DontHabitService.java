package com.example.dobit.src.dontHabit;

import com.example.dobit.config.BaseException;
import com.example.dobit.src.dontElse.DontElseRepository;
import com.example.dobit.src.dontElse.models.DontElse;
import com.example.dobit.src.dontHabit.models.DontHabit;
import com.example.dobit.src.dontHabit.models.PatchIdentityDontHabitReq;
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

    /**
     * 정체성별 Dont 습관 수정하기 API
     * @param dontHabit, patchIdentityDontHabitReq
     * @return void
     * @throws BaseException
     */
    @Transactional
    public void  updateIdentityDontHabit(DontHabit dontHabit, PatchIdentityDontHabitReq patchIdentityDontHabitReq) throws BaseException {

        String dontName = patchIdentityDontHabitReq.getDontName();
        String dontAdvantage = patchIdentityDontHabitReq.getDontAdvantage();
        String dontEnv = patchIdentityDontHabitReq.getDontEnv();
        List<String> dontRoutineList = patchIdentityDontHabitReq.getDontRoutine();
        List<String> dontMotiveList = patchIdentityDontHabitReq.getDontMotive();
        List<String> dontElseList = patchIdentityDontHabitReq.getDontElse();

        try{
            dontHabit.setDnhName(dontName);
            dontHabit.setDnhAdvantage(dontAdvantage);
            dontHabit.setDnhEnv(dontEnv);
            dontHabitRepository.save(dontHabit);
        }catch(Exception e){
            throw new BaseException(FAILED_TO_SAVE_DONT_HABIT);
        }


        List<DontRoutine> dontRoutines;
        try {
            dontRoutines = dontRoutineRepository.findByDontHabitAndStatus(dontHabit,"ACTIVE");
        }catch (Exception e){
            throw new BaseException(FAILED_TO_FIND_BY_DONTHABIT_AND_STATUS);
        }
        try{
            for(int i=0;i<dontRoutines.size();i++){
                dontRoutines.get(i).setStatus("INACTIVE");
            }
            dontRoutineRepository.saveAll(dontRoutines);
        }catch (Exception e){
            throw new BaseException(FAILED_TO_DELETE_DONT_ROUTINE);
        }
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

        List<DontMotive> dontMotives;
        try {
            dontMotives = dontMotiveRepository.findByDontHabitAndStatus(dontHabit,"ACTIVE");
        }catch (Exception e){
            throw new BaseException(FAILED_TO_FIND_BY_DONTHABIT_AND_STATUS);
        }

        try{
            for(int i=0;i<dontMotives.size();i++){
                dontMotives.get(i).setStatus("INACTIVE");
            }
            dontMotiveRepository.saveAll(dontMotives);
        }catch (Exception e){
            throw new BaseException(FAILED_TO_DELETE_DONT_MOTIVE);
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


        List<DontElse> dontElses;
        try {
            dontElses = dontElseRepository.findByDontHabitAndStatus(dontHabit,"ACTIVE");
        }catch (Exception e){
            throw new BaseException(FAILED_TO_FIND_BY_DONTHABIT_AND_STATUS);
        }

        try{
            for(int i=0;i<dontElses.size();i++){
                dontElses.get(i).setStatus("INACTIVE");
            }
            dontElseRepository.saveAll(dontElses);
        }catch (Exception e){
            throw new BaseException(FAILED_TO_DELETE_DONT_ELSE);
        }
        if (dontElseList!=null){
            for(int i=0;i<dontElseList.size();i++){
                String dnElseContent = dontElseList.get(i);
                DontElse dontElse = new DontElse(dnElseContent,dontHabit);
                try {
                    dontElseRepository.save(dontElse);
                }catch (Exception e){
                    throw new BaseException(FAILED_TO_SAVE_DONT_ELSE);
                }
            }
        }


    }

}
