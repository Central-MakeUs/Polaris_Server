package com.example.dobit.src.dontHabit;

import com.example.dobit.config.BaseException;
import com.example.dobit.config.BaseResponse;
import com.example.dobit.src.doHabit.models.DoHabit;
import com.example.dobit.src.doHabit.models.GetIdentityDoHabitRes;
import com.example.dobit.src.dontHabit.models.DontHabit;
import com.example.dobit.src.dontHabit.models.GetIdentityDontHabitRes;
import com.example.dobit.src.dontHabit.models.PostIdentityDontHabitReq;
import com.example.dobit.src.user.UserInfoProvider;
import com.example.dobit.src.user.models.UserInfo;
import com.example.dobit.src.userIdentity.UserIdentityProvider;
import com.example.dobit.src.userIdentity.models.UserIdentity;
import com.example.dobit.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.dobit.config.BaseResponseStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class DontHabitController {
    private final DontHabitProvider dontHabitProvider;
    private final DontHabitService dontHabitService;
    private final DontHabitRepository dontHabitRepository;
    private final JwtService jwtService;
    private final UserInfoProvider userInfoProvider;
    private final UserIdentityProvider userIdentityProvider;


    /**
     * 정체성별 Dont 습관 입력하기 API
     * [POST] /identity/:userIdentityIdx/donothabit
     * @PathVariable userIdentityIdx
     * @RequestBody PostIdentityDontHabitReq
     * @return BaseResponse<Void>
     */
    @ResponseBody
    @PostMapping("/identity/{userIdentityIdx}/donthabit")
    public BaseResponse<Void> postIdentityDontHabit(@PathVariable Integer userIdentityIdx, @RequestBody PostIdentityDontHabitReq postIdentityDontHabitReq) throws BaseException {

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

        Boolean existDontHabit = dontHabitProvider.retrieveExistingDontHabitByUserIdentity(userIdentity);
        if(existDontHabit){
            return new BaseResponse<>(EXIST_DONT_HABIT);
        }

        String dontName = postIdentityDontHabitReq.getDontName();
        String dontAdvantage = postIdentityDontHabitReq.getDontAdvantage();
        String dontEnv = postIdentityDontHabitReq.getDontEnv();
        List<String> dontRoutine = postIdentityDontHabitReq.getDontRoutine();
        List<String> dontMotive = postIdentityDontHabitReq.getDontMotive();
        List<String> dontElse = postIdentityDontHabitReq.getDontElse();

        if(dontName == null || dontName.length() == 0){
            return new BaseResponse<>(EMPTY_DONT_NAME);
        }

        if(dontName.length() >= 45){
            return new BaseResponse<>(INVALID_DONT_NAME);
        }

        if(dontAdvantage == null || dontAdvantage.length() == 0){
            return new BaseResponse<>(EMPTY_DONT_ADVANTAGE);
        }

        if(dontAdvantage.length() >= 45){
            return new BaseResponse<>(INVALID_DONT_ADVANTAGE);
        }


        if(dontEnv == null || dontEnv.length() == 0){
            return new BaseResponse<>(EMPTY_DONT_ENV);
        }

        if(dontEnv.length() >= 45){
            return new BaseResponse<>(INVALID_DONT_ENV);
        }


        if( dontRoutine!=null){
            for (int i=0;i<dontRoutine.size();i++){
                if(dontRoutine.get(i).length() >= 45){
                    return new BaseResponse<>(INVALID_DONT_ROUTINE);
                }
            }
        }

        if(dontMotive!=null){
            for (int i=0;i<dontMotive.size();i++){
                if(dontMotive.get(i).length() >= 45){
                    return new BaseResponse<>(INVALID_DONT_MOTIVE);
                }
            }
        }

        if(dontElse!=null){
            for (int i=0;i<dontElse.size();i++){
                if(dontElse.get(i).length() >= 45){
                    return new BaseResponse<>(INVALID_DONT_ELSE);
                }
            }
        }



        try {
            dontHabitService.createIdentityDontHabit(userIdentity,postIdentityDontHabitReq);
            return new BaseResponse<>(SUCCESS);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }


    }

    /**
     * 정체성별 Dont 습관 조회하기 API
     * [GET] /donthabit/:dnhIdx
     * @PathVariable dnhIdx
     * @return BaseResponse<GetIdentityDontHabitRes>
     */
    @ResponseBody
    @GetMapping("/donthabit/{dnhIdx}")
    public BaseResponse<GetIdentityDontHabitRes> getIdentityDoHabit(@PathVariable Integer dnhIdx) throws BaseException {

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

//        UserIdentity userIdentity = userIdentityProvider.retrieveUserIdentityByUserIdentityIdx(userIdentityIdx);
//        if(userIdentity==null){
//            return new BaseResponse<>(INVALID_USER_IDENTITY);
//        }
//
//        Boolean existUserIdentity = userIdentityProvider.retrieveExistingUserIdentity(userInfo,userIdentityIdx);
//        if (existUserIdentity == null){
//            return new BaseResponse<>(DO_NOT_MATCH_USER_AND_USERIDENTITYIDX);
//        }

        DontHabit dontHabit = dontHabitProvider.retrieveDontHabitByDnhIdx(dnhIdx);
        if(dontHabit==null){
            return new BaseResponse<>(INVALID_DONT_HABIT);
        }



        GetIdentityDontHabitRes getIdentityDontHabitRes;
        try {
            getIdentityDontHabitRes = dontHabitProvider.retrieveIdentityDontHabit(dontHabit);
            return new BaseResponse<>(SUCCESS,getIdentityDontHabitRes);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }



}
