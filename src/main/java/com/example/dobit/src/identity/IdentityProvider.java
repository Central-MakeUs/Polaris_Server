package com.example.dobit.src.identity;

import com.example.dobit.config.BaseException;
import com.example.dobit.src.identity.models.Identity;
import com.example.dobit.src.identity.models.GetOriginIdentityRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.dobit.config.BaseResponseStatus.FAILED_TO_FIND_BY_STATUS;


@Service
@RequiredArgsConstructor
public class IdentityProvider {
    private final IdentityRepository identityRepository;

    /**
     * 기존 목표 조회하기 API
     *
     * @return List<GetOriginIdentityRes>
     * @throws BaseException
     */
    public List<GetOriginIdentityRes> retrieveOriginIdentity() throws BaseException {
        List<Identity> identityList;
        try {
            identityList = identityRepository.findByStatus("ACTIVE");
        } catch (Exception ignored) {
            throw new BaseException(FAILED_TO_FIND_BY_STATUS);
        }

        List<GetOriginIdentityRes> getOriginIdentityResList = new ArrayList<>();
        for (int i = 0; i < identityList.size(); i++) {
            Integer identityIdx = identityList.get(i).getIdentityIdx();
            String identityName = identityList.get(i).getIdentityName();

            GetOriginIdentityRes getOriginIdentityRes = new GetOriginIdentityRes(identityIdx, identityName);
            getOriginIdentityResList.add(getOriginIdentityRes);

        }
        return getOriginIdentityResList;
    }
}