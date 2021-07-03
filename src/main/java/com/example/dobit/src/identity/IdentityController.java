package com.example.dobit.src.identity;

import com.example.dobit.config.BaseException;
import com.example.dobit.config.BaseResponse;
import com.example.dobit.src.identity.models.GetOriginIdentityRes;
import com.example.dobit.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.dobit.config.BaseResponseStatus.SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class IdentityController {
    private final JwtService jwtService;
    private final IdentityProvider identityProvider;

    /**
     * 기존 목표 조회하기 API
     * [POST] /origin-identity
     * @return BaseResponse<GetOriginIdentityRes>
     */
    @ResponseBody
    @GetMapping("/origin-identity")
    public BaseResponse<List<GetOriginIdentityRes>> getOriginIdentity()  {
        Integer jwtUserIdx;
        try {
            jwtUserIdx = jwtService.getUserIdx();
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }

        try {
            List<GetOriginIdentityRes> getOriginIdentityResList = identityProvider.retrieveOriginIdentity();
            return new BaseResponse<>(SUCCESS,getOriginIdentityResList);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }

    }



}
