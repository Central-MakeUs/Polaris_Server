package com.example.dobit.src.doHabit;

import com.example.dobit.config.BaseException;
import com.example.dobit.config.BaseResponse;
import com.example.dobit.src.doHabit.models.PostIdentityDoHabitReq;
import com.example.dobit.src.user.UserInfoProvider;
import com.example.dobit.src.user.models.UserInfo;
import com.example.dobit.src.userIdentity.UserIdentityProvider;
import com.example.dobit.src.userIdentity.models.UserIdentity;
import com.example.dobit.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import static com.example.dobit.config.BaseResponseStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class DoHabitController {
    private final DoHabitProvider doHabitProvider;
    private final DoHabitService doHabitService;
    private final JwtService jwtService;
    private final UserInfoProvider userInfoProvider;
    private final UserIdentityProvider userIdentityProvider;


    /**
     * 정체성별 Do 습관 입력하기 API
     * [POST] /identity/:userIdentityIdx/dohabit
     * @PathVariable userIdentityIdx
     * @RequestBody PostIdentityDoHabitReq
     * @return BaseResponse<Void>
     */
    @ResponseBody
    @PostMapping("/identity/{userIdentityIdx}/dohabit")
    public BaseResponse<Void> postIdentityDoHabit(@PathVariable Integer userIdentityIdx, @RequestBody PostIdentityDoHabitReq postIdentityDoHabitReq) throws BaseException {

        Integer jwtUserIdx;
        try {
            jwtUserIdx = jwtService.getUserIdx();
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
        UserInfo userInfo = userInfoProvider.retrieveUserByUserIdx(jwtUserIdx);
        if(userInfo == null){
            return new BaseResponse<>(INVALID_USER);
        }

        UserIdentity userIdentity = userIdentityProvider.retrieveUserIdentityByUserIdentityIdx(userIdentityIdx);
        if(userIdentity==null){
            return new BaseResponse<>(INVALID_USER_IDENTITY);
        }

        Boolean existUserIdentity = userIdentityProvider.retrieveExistingUserIdentity(userInfo,userIdentityIdx);
        if (existUserIdentity == null){
            return new BaseResponse<>(DO_NOT_MATCH_USER_AND_USERIDENTITYIDX);
        }

        Boolean existDoHabit = doHabitProvider.retrieveExistingDoHabitByUserIdentity(userIdentity);
        if(existDoHabit){
            return new BaseResponse<>(EXIST_DO_HABIT);
        }

        if(postIdentityDoHabitReq.getDoName() == null || postIdentityDoHabitReq.getDoName().length() == 0){
            return new BaseResponse<>(EMPTY_DO_NAME);
        }

        if(postIdentityDoHabitReq.getDoName().length() >= 45){
            return new BaseResponse<>(INVALID_DO_NAME);
        }

        if(postIdentityDoHabitReq.getDoWhen() == null || postIdentityDoHabitReq.getDoWhen().length() == 0){
            return new BaseResponse<>(EMPTY_DO_WHEN);
        }

        if(postIdentityDoHabitReq.getDoWhen().length() >= 45){
            return new BaseResponse<>(INVALID_DO_WHEN);
        }


        if(postIdentityDoHabitReq.getDoWhere() == null || postIdentityDoHabitReq.getDoWhere().length() == 0){
            return new BaseResponse<>(EMPTY_DO_WHERE);
        }

        if(postIdentityDoHabitReq.getDoWhere().length() >= 45){
            return new BaseResponse<>(INVALID_DO_WHERE);
        }

        if(postIdentityDoHabitReq.getDoStart() == null || postIdentityDoHabitReq.getDoStart().length() == 0){
            return new BaseResponse<>(EMPTY_DO_START);
        }

        if(postIdentityDoHabitReq.getDoStart().length() >= 45){
            return new BaseResponse<>(INVALID_DO_START);
        }

        if( postIdentityDoHabitReq.getDoRoutine()!=null){
            for (int i=0;i<postIdentityDoHabitReq.getDoRoutine().size();i++){
                if(postIdentityDoHabitReq.getDoRoutine().get(i).length() >= 45){
                    return new BaseResponse<>(INVALID_DO_ROUTINE);
                }
            }
        }

        if(postIdentityDoHabitReq.getDoEnv()!=null){
            for (int i=0;i<postIdentityDoHabitReq.getDoEnv().size();i++){
                if(postIdentityDoHabitReq.getDoEnv().get(i).length() >= 45){
                    return new BaseResponse<>(INVALID_DO_ENV);
                }
            }
        }

        if(postIdentityDoHabitReq.getDoNext()!=null){
            for (int i=0;i<postIdentityDoHabitReq.getDoNext().size();i++){
                if(postIdentityDoHabitReq.getDoNext().get(i).length() >= 45){
                    return new BaseResponse<>(INVALID_DO_NEXT);
                }
            }
        }

        if(postIdentityDoHabitReq.getDoElse()!=null){
            for (int i=0;i<postIdentityDoHabitReq.getDoElse().size();i++){
                if(postIdentityDoHabitReq.getDoElse().get(i).length() >= 45){
                    return new BaseResponse<>(INVALID_DO_ELSE);
                }
            }
        }



        try {
            doHabitService.createIdentityDoHabit(userIdentity,postIdentityDoHabitReq);
            return new BaseResponse<>(SUCCESS);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }


    }



//    /**
//     * 정체성별 Do 습관 수정하기 API
//     * [PATCH] /identity/:userIdentityIdx/dohabit
//     * @PathVariable userIdentityIdx
//     * @RequestBody PatchIdentityDoHabitReq
//     * @return BaseResponse<Void>
//     */
//    @ResponseBody
//    @PatchMapping("/identity/{userIdentityIdx}/dohabit")
//    public BaseResponse<Void> patchIdentityDoHabit(@PathVariable Integer userIdentityIdx, @RequestBody PatchIdentityDoHabitReq patchIdentityDoHabitReq) throws BaseException {
//
//        Integer jwtUserIdx;
//        try {
//            jwtUserIdx = jwtService.getUserIdx();
//        } catch (BaseException exception) {
//            return new BaseResponse<>(exception.getStatus());
//        }
//        UserInfo userInfo = userInfoProvider.retrieveUserByUserIdx(jwtUserIdx);
//        if(userInfo == null){
//            return new BaseResponse<>(INVALID_USER);
//        }
//
//        UserIdentity userIdentity = userIdentityProvider.retrieveUserIdentityByUserIdentityIdx(userIdentityIdx);
//        if(userIdentity==null){
//            return new BaseResponse<>(INVALID_USER_IDENTITY);
//        }
//
//        Boolean existUserIdentity = userIdentityProvider.retrieveExistingUserIdentity(userInfo,userIdentityIdx);
//        if (existUserIdentity == null){
//            return new BaseResponse<>(DO_NOT_MATCH_USER_AND_USERIDENTITYIDX);
//        }
//
//        Boolean existDoHabit = doHabitProvider.retrieveExistingDoHabitByUserIdentity(userIdentity);
//        if(existDoHabit){
//            return new BaseResponse<>(EXIST_DO_HABIT);
//        }
//
//        if(patchIdentityDoHabitReq.getDoName() == null || patchIdentityDoHabitReq.getDoName().length() == 0){
//            return new BaseResponse<>(EMPTY_DO_NAME);
//        }
//
//        if(patchIdentityDoHabitReq.getDoName().length() >= 45){
//            return new BaseResponse<>(INVALID_DO_NAME);
//        }
//
//        if(patchIdentityDoHabitReq.getDoWhen() == null || patchIdentityDoHabitReq.getDoWhen().length() == 0){
//            return new BaseResponse<>(EMPTY_DO_WHEN);
//        }
//
//        if(patchIdentityDoHabitReq.getDoWhen().length() >= 45){
//            return new BaseResponse<>(INVALID_DO_WHEN);
//        }
//
//
//        if(patchIdentityDoHabitReq.getDoWhere() == null || patchIdentityDoHabitReq.getDoWhere().length() == 0){
//            return new BaseResponse<>(EMPTY_DO_WHERE);
//        }
//
//        if(patchIdentityDoHabitReq.getDoWhere().length() >= 45){
//            return new BaseResponse<>(INVALID_DO_WHERE);
//        }
//
//        if(patchIdentityDoHabitReq.getDoStart() == null || patchIdentityDoHabitReq.getDoStart().length() == 0){
//            return new BaseResponse<>(EMPTY_DO_START);
//        }
//
//        if(patchIdentityDoHabitReq.getDoStart().length() >= 45){
//            return new BaseResponse<>(INVALID_DO_START);
//        }
//
//        if( patchIdentityDoHabitReq.getDoRoutine()!=null){
//            for (int i=0;i<patchIdentityDoHabitReq.getDoRoutine().size();i++){
//                if(patchIdentityDoHabitReq.getDoRoutine().get(i).length() >= 45){
//                    return new BaseResponse<>(INVALID_DO_ROUTINE);
//                }
//            }
//        }
//
//        if(patchIdentityDoHabitReq.getDoEnv()!=null){
//            for (int i=0;i<patchIdentityDoHabitReq.getDoEnv().size();i++){
//                if(patchIdentityDoHabitReq.getDoEnv().get(i).length() >= 45){
//                    return new BaseResponse<>(INVALID_DO_ENV);
//                }
//            }
//        }
//
//        if(patchIdentityDoHabitReq.getDoNext()!=null){
//            for (int i=0;i<patchIdentityDoHabitReq.getDoNext().size();i++){
//                if(patchIdentityDoHabitReq.getDoNext().get(i).length() >= 45){
//                    return new BaseResponse<>(INVALID_DO_NEXT);
//                }
//            }
//        }
//
//        if(patchIdentityDoHabitReq.getDoElse()!=null){
//            for (int i=0;i<patchIdentityDoHabitReq.getDoElse().size();i++){
//                if(patchIdentityDoHabitReq.getDoElse().get(i).length() >= 45){
//                    return new BaseResponse<>(INVALID_DO_ELSE);
//                }
//            }
//        }
//
//
//
//        try {
//            doHabitService.updateIdentityDoHabit(userIdentity,patchIdentityDoHabitReq);
//            return new BaseResponse<>(SUCCESS);
//        } catch (BaseException exception) {
//            return new BaseResponse<>(exception.getStatus());
//        }
//
//
//    }


}
