package com.example.dobit.src.dontHabitCheck;

import com.example.dobit.config.BaseException;
import com.example.dobit.config.BaseResponse;
import com.example.dobit.src.dontHabit.DontHabitProvider;
import com.example.dobit.src.dontHabit.models.DontHabit;
import com.example.dobit.src.dontHabitCheck.models.PostDontHabitCheckReq;
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
public class DontHabitCheckController {
    private final JwtService jwtService;
    private final DontHabitCheckProvider dontHabitCheckProvider;
    private final DontHabitCheckService dontHabitCheckService;
    private final UserInfoProvider userInfoProvider;
    private final DontHabitProvider dontHabitProvider;




    /**
     * Don't 습관 체크하기 API
     * [POST] /donthabit/check
     * @return BaseResponse<Void>
     * @RequestBody postDontHabitCheckReq
     */
    @PostMapping("/donthabit/check")
    public BaseResponse<Void> postDoHabitCheck(@RequestBody PostDontHabitCheckReq postDontHabitCheckReq) throws BaseException {
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


        Integer dontHabitIdx = postDontHabitCheckReq.getDontHabitIdx();

        if (dontHabitIdx == null || dontHabitIdx <= 0) {
            return new BaseResponse<>(EMPTY_DONTHABITIDX);
        }
        DontHabit dontHabit = dontHabitProvider.retrieveDontHabitByDnhIdx(dontHabitIdx);
        if(dontHabit ==null){
            return new BaseResponse<>(INVALID_DONT_HABIT);
        }
        Boolean existDontHabitCheck = dontHabitCheckProvider.retrieveExistingDontHabitCheckToCurrentDate(userInfo,dontHabit);
        try {
            if(existDontHabitCheck){
                dontHabitCheckService.deleteDontHabitCheck(userInfo,dontHabit);
            }
            else{
                dontHabitCheckService.createDontHabitCheck(userInfo,dontHabit);
            }
            return new BaseResponse<>(SUCCESS);
        }catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }





    }

}
