package com.example.dobit.src.doHabitCheck;

import com.example.dobit.config.BaseException;
import com.example.dobit.config.BaseResponse;
import com.example.dobit.src.doHabit.DoHabitProvider;
import com.example.dobit.src.doHabit.models.DoHabit;
import com.example.dobit.src.doHabitCheck.models.PostDoHabitCheckReq;
import com.example.dobit.src.user.UserInfoProvider;
import com.example.dobit.src.user.models.UserInfo;
import com.example.dobit.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.dobit.config.BaseResponseStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class DoHabitCheckController {
    private final JwtService jwtService;
    private final UserInfoProvider userInfoProvider;
    private final DoHabitProvider doHabitProvider;
    private final DoHabitCheckProvider doHabitCheckProvider;
    private final DoHabitCheckService doHabitCheckService;



    /**
     * Do 습관 체크하기 API
     * [POST] /dohabit/check
     * @return BaseResponse<Void>
     * @RequestBody postDoHabitCheckReq
     */
    @PostMapping("/dohabit/check")
    public BaseResponse<Void> postDoHabitCheck(@RequestBody PostDoHabitCheckReq postDoHabitCheckReq) throws BaseException {
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


        Integer doHabitIdx = postDoHabitCheckReq.getDoHabitIdx();

        if (doHabitIdx == null || doHabitIdx <= 0) {
            return new BaseResponse<>(EMPTY_DOHABITIDX);
        }
        DoHabit doHabit = doHabitProvider.retrieveDoHabitByDhIdx(doHabitIdx);
        if(doHabit ==null){
            return new BaseResponse<>(INVALID_DO_HABIT);
        }


        // 현재날짜에 체크한 게있다면
        Boolean existDoHabitCheck = doHabitCheckProvider.retrieveExistingDoHabitCheckToCurrentDate(userInfo,doHabit);
        try {
            if(existDoHabitCheck){
                doHabitCheckService.deleteDoHabitCheck(userInfo,doHabit);
            }
            else{
                doHabitCheckService.createDoHabitCheck(userInfo,doHabit);
            }
            return new BaseResponse<>(SUCCESS);
        }catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }


    }
}
