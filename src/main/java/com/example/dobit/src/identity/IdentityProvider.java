package com.example.dobit.src.identity;

import com.example.dobit.config.BaseException;
import com.example.dobit.src.identity.models.Identity;
import com.example.dobit.src.identity.models.GetIdentityExampleRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.dobit.config.BaseResponseStatus.*;


@Service
@RequiredArgsConstructor
public class IdentityProvider {
    private final IdentityRepository identityRepository;

    /**
     * 정체성 예시 조회하기 API
     *
     * @return List<GetIdentityExampleRes>
     * @throws BaseException
     */
    public List<GetIdentityExampleRes> retrieveOriginIdentity() throws BaseException {
        List<Identity> identityList;
        try {
            identityList = identityRepository.findByStatus("ACTIVE");
        } catch (Exception ignored) {
            throw new BaseException(FAILED_TO_FIND_BY_STATUS);
        }

        List<GetIdentityExampleRes> getIdentityExampleResList = new ArrayList<>();
        for (int i = 0; i < identityList.size(); i++) {
            Integer identityIdx = identityList.get(i).getIdentityIdx();
            String identityName = identityList.get(i).getIdentityName();

            GetIdentityExampleRes getIdentityExampleRes = new GetIdentityExampleRes(identityIdx, identityName);
            getIdentityExampleResList.add(getIdentityExampleRes);

        }
        return getIdentityExampleResList;
    }

    /**
     * Idx로 정체성 조회
     * @param identityIdx
     * @return Identity
     * @throws BaseException
     */
    public Identity retrieveIdentityByIdentityIdx(Integer identityIdx) throws BaseException {
        Identity identity;
        try {
            identity = identityRepository.findById(identityIdx).orElse(null);
        } catch (Exception ignored) {
            throw new BaseException(FAILED_TO_FIND_BY_IDENTITYIDX);
        }

        if (identity == null || !identity.getStatus().equals("ACTIVE")) {
            throw new BaseException(INACTIVE_IDENTITY);
        }

        return identity;
    }
}