package com.example.dobit.src.identity;

import com.example.dobit.config.BaseException;
import com.example.dobit.config.BaseResponse;
import com.example.dobit.src.identity.models.GetIdentityExampleRes;
import com.example.dobit.src.user.UserInfoProvider;
import com.example.dobit.src.user.models.UserInfo;
import com.example.dobit.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.dobit.config.BaseResponseStatus.INVALID_USER;
import static com.example.dobit.config.BaseResponseStatus.SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class IdentityController {
    private final JwtService jwtService;
    private final IdentityProvider identityProvider;
    private final UserInfoProvider userInfoProvider;

    /**
     * 정체성 예시 조회하기 API
     * [POST] /identity/example
     * @return BaseResponse<GetIdentityExampleRes>
     */
    @ResponseBody
    @GetMapping("/identity/example")
    public BaseResponse<List<GetIdentityExampleRes>> getIdentityExample() throws BaseException {
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

        try {
            List<GetIdentityExampleRes> getIdentityExampleResList = identityProvider.retrieveOriginIdentity();
            return new BaseResponse<>(SUCCESS, getIdentityExampleResList);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }

    }





}
