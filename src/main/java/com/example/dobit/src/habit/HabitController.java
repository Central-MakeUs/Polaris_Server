package com.example.dobit.src.habit;

import com.example.dobit.config.BaseException;
import com.example.dobit.config.BaseResponse;
import com.example.dobit.src.habit.models.GetHabitRes;
import com.example.dobit.src.user.UserInfoProvider;
import com.example.dobit.src.user.models.UserInfo;
import com.example.dobit.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.dobit.config.BaseResponseStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class HabitController {
    private final JwtService jwtService;
    private final UserInfoProvider userInfoProvider;
    private final HabitProvider habitProvider;

    /**
     * 습관 조회하기 API
     * [GET] /habit
     * @return BaseResponse<GetHabitRes>
     */
    @ResponseBody
    @GetMapping("/habit")
    public BaseResponse<List<GetHabitRes>> getHabit() throws BaseException {

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




        List<GetHabitRes> getHabitResList;
        try {
            getHabitResList = habitProvider.retrieveHabit(userInfo);
            return new BaseResponse<>(SUCCESS,getHabitResList);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }


}
