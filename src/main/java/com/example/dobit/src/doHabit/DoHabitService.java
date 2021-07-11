package com.example.dobit.src.doHabit;

import com.example.dobit.config.BaseException;
import com.example.dobit.src.doElse.DoElseRepository;
import com.example.dobit.src.doElse.models.DoElse;
import com.example.dobit.src.doEnv.DoEnvRepository;
import com.example.dobit.src.doEnv.models.DoEnv;
import com.example.dobit.src.doHabit.models.DoHabit;
import com.example.dobit.src.doHabit.models.PostIdentityDoHabitReq;
import com.example.dobit.src.doNext.DoNextRepository;
import com.example.dobit.src.doNext.models.DoNext;
import com.example.dobit.src.doRoutine.DoRoutineRepository;
import com.example.dobit.src.doRoutine.models.DoRoutine;
import com.example.dobit.src.userIdentity.models.UserIdentity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.dobit.config.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class DoHabitService {
    private final DoHabitProvider doHabitProvider;
    private final DoHabitRepository doHabitRepository;
    private final DoRoutineRepository doRoutineRepository;
    private final DoEnvRepository doEnvRepository;
    private final DoNextRepository doNextRepository;
    private final DoElseRepository doElseRepository;

    /**
     * 정체성별 Do 습관 입력하기 API
     * @param userIdentity, postIdentityDoHabitReq
     * @return void
     * @throws BaseException
     */
    @Transactional
    public void  createIdentityDoHabit(UserIdentity userIdentity, PostIdentityDoHabitReq postIdentityDoHabitReq) throws BaseException {
        String doName = postIdentityDoHabitReq.getDoName();
        String doWhen = postIdentityDoHabitReq.getDoWhen();
        String doWhere = postIdentityDoHabitReq.getDoWhere();
        String doStart = postIdentityDoHabitReq.getDoStart();
        List<String> doRoutineList = postIdentityDoHabitReq.getDoRoutine();
        List<String> doEnvList = postIdentityDoHabitReq.getDoEnv();
        List<String> doNextList = postIdentityDoHabitReq.getDoNext();
        List<String> doElseList = postIdentityDoHabitReq.getDoElse();

        DoHabit dh = new DoHabit(doName,doWhen,doWhere,doStart,userIdentity);
        Integer newDhIdx;
        try{
            doHabitRepository.save(dh);
            newDhIdx = dh.getDhIdx();
        }catch (Exception exception){
            throw new BaseException(FAILED_TO_SAVE_DO_HABIT);
        }

        DoHabit doHabit = doHabitProvider.retrieveDoHabitByDhIdx(newDhIdx);

        if (doRoutineList!=null){
            for(int i =0;i<doRoutineList.size();i++) {
                String dRoutineContent = doRoutineList.get(i);
                DoRoutine doRoutine = new DoRoutine(dRoutineContent, doHabit);
                try{
                    doRoutineRepository.save(doRoutine);
                }catch (Exception exception){
                    throw new BaseException(FAILED_TO_SAVE_DO_ROUTINE);
                }
            }
        }

        if (doEnvList!=null){
            for(int i=0;i<doEnvList.size();i++){
                String dEnvContent = doEnvList.get(i);
                DoEnv doEnv = new DoEnv(dEnvContent,doHabit);
                try{
                    doEnvRepository.save(doEnv);
                }catch(Exception exception) {
                    throw new BaseException(FAILED_TO_SAVE_DO_ENV);
                }
            }
        }
        if (doNextList!=null){
            for(int i=0;i<doNextList.size();i++){
                String dNextContent = doNextList.get(i);
                DoNext doNext = new DoNext(dNextContent,doHabit);
                try {
                    doNextRepository.save(doNext);
                }catch (Exception e){
                    throw new BaseException(FAILED_TO_SAVE_DO_NEXT);
                }
            }

        }
        if (doElseList!=null){
            for(int i=0;i<doElseList.size();i++){
                String dElseContent = doElseList.get(i);
                DoElse doElse = new DoElse(dElseContent,doHabit);
                try {
                    doElseRepository.save(doElse);
                }catch (Exception e){
                    throw new BaseException(FAILED_TO_SAVE_DO_ELSE);
                }
            }
        }

    }
//    /**
//     * 정체성별 Do 습관 수정하기 API
//     * @param userIdentity, patchIdentityDoHabitReq
//     * @return void
//     * @throws BaseException
//     */
//    @Transactional
//    public void  updateIdentityDoHabit(UserIdentity userIdentity, PatchIdentityDoHabitReq patchIdentityDoHabitReq) throws BaseException {
//        String doName = patchIdentityDoHabitReq.getDoName();
//        String doWhen = patchIdentityDoHabitReq.getDoWhen();
//        String doWhere = patchIdentityDoHabitReq.getDoWhere();
//        String doStart = patchIdentityDoHabitReq.getDoStart();
//        List<String> doRoutineList = patchIdentityDoHabitReq.getDoRoutine();
//        List<String> doEnvList = patchIdentityDoHabitReq.getDoEnv();
//        List<String> doNextList = patchIdentityDoHabitReq.getDoNext();
//        List<String> doElseList = patchIdentityDoHabitReq.getDoElse();
//
//
//        // 습관 조회
//
////        DoHabit dh = new DoHabit(doName,doWhen,doWhere,doStart,userIdentity);
////        Integer newDhIdx;
////        try{
////            doHabitRepository.save(dh);
////            newDhIdx = dh.getDhIdx();
////        }catch (Exception exception){
////            throw new BaseException(FAILED_TO_SAVE_DO_HABIT);
////        }
////
////        DoHabit doHabit = doHabitProvider.retrieveDoHabitByDhIdx(newDhIdx);
////
////        if (doRoutineList!=null){
////            for(int i =0;i<doRoutineList.size();i++) {
////                String dRoutineContent = doRoutineList.get(i);
////                DoRoutine doRoutine = new DoRoutine(dRoutineContent, doHabit);
////                try{
////                    doRoutineRepository.save(doRoutine);
////                }catch (Exception exception){
////                    throw new BaseException(FAILED_TO_SAVE_DO_ROUTINE);
////                }
////            }
////        }
////
////        if (doEnvList!=null){
////            for(int i=0;i<doEnvList.size();i++){
////                String dEnvContent = doEnvList.get(i);
////                DoEnv doEnv = new DoEnv(dEnvContent,doHabit);
////                try{
////                    doEnvRepository.save(doEnv);
////                }catch(Exception exception) {
////                    throw new BaseException(FAILED_TO_SAVE_DO_ENV);
////                }
////            }
////        }
////        if (doNextList!=null){
////            for(int i=0;i<doNextList.size();i++){
////                String dNextContent = doNextList.get(i);
////                DoNext doNext = new DoNext(dNextContent,doHabit);
////                try {
////                    doNextRepository.save(doNext);
////                }catch (Exception e){
////                    throw new BaseException(FAILED_TO_SAVE_DO_NEXT);
////                }
////            }
////
////        }
////        if (doElseList!=null){
////            for(int i=0;i<doElseList.size();i++){
////                String dElseContent = doElseList.get(i);
////                DoElse doElse = new DoElse(dElseContent,doHabit);
////                try {
////                    doElseRepository.save(doElse);
////                }catch (Exception e){
////                    throw new BaseException(FAILED_TO_SAVE_DO_ELSE);
////                }
////            }
////        }
//
//    }

}
