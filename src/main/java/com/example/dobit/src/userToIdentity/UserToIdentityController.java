package com.example.dobit.src.userToIdentity;

import com.example.dobit.config.BaseException;
import com.example.dobit.config.BaseResponse;
import com.example.dobit.src.identity.IdentityProvider;
import com.example.dobit.src.identity.models.Identity;
import com.example.dobit.src.user.UserInfoProvider;
import com.example.dobit.src.user.models.UserInfo;
import com.example.dobit.src.userToIdentity.models.PostIdentityReq;
import com.example.dobit.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.dobit.config.BaseResponseStatus.*;


@RestController
@RequiredArgsConstructor
@RequestMapping
public class UserToIdentityController {
    private final JwtService jwtService;
    private final UserInfoProvider userInfoProvider;
    private final IdentityProvider identityProvider;
    private final UserToIdentityService userToIdentityService;

    /**
     * 목표 추가하기 API
     * [POST] /identity
     * @RequestBody postIdentityReq
     * @return BaseResponse<PostIdentityRes>
     */
    @ResponseBody
    @PostMapping("/identity")
    public BaseResponse<Void> postIdentity(@RequestBody PostIdentityReq postIdentityReq) throws BaseException {
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

        List<Integer> identityList = postIdentityReq.getIdentityList();

        if(identityList.isEmpty() || identityList == null){
            return new BaseResponse<>(EMPTY_IDENTITYLIST);
        }

        for (int i = 0; i < identityList.size(); i++) {
            Integer identityIdx = identityList.get(i);

            Identity identity = identityProvider.retrieveIdentityByIdentityIdx(identityIdx);
            if(identity == null){
                return new BaseResponse<>(INVALID_IDENTITYIDX);
            }

        }
        try {
            userToIdentityService.createIdentity(userInfo,postIdentityReq);
            return new BaseResponse<>(SUCCESS);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }

    }



}
