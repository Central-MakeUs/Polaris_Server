package com.example.dobit.src.userToIdentity.models;

import com.example.dobit.config.BaseEntity;
import com.example.dobit.src.identity.models.Identity;
import com.example.dobit.src.user.models.UserInfo;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@NoArgsConstructor(access = AccessLevel.PUBLIC) // Unit Test 를 위해 PUBLIC
@EqualsAndHashCode(callSuper = false)
@Data // from lombok
@Entity // 필수, Class 를 Database Table화 해주는 것이다
@Table(name = "UserToIdentity") // Table 이름을 명시해주지 않으면 class 이름을 Table 이름으로 대체한다.
public class UserToIdentity extends BaseEntity {
    /**
     * idx
     */
    @Id // PK를 의미하는 어노테이션
    @Column(name = "userToIdentityIdx", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userToIdentityIdx;

    /**
     * 유저
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userIdx", nullable = false)
    private UserInfo userInfo;

    /**
     * 정체성
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "identityIdx")
    private Identity identity;

    /**
     * 정체성명
     */
    @Column(name = "identityName", nullable = false, length = 45)
    private String identityName;


    /**
     * 상태
     */
    @Column(name = "status", nullable = false, length = 10)
    private String status = "ACTIVE";


    public UserToIdentity(UserInfo userInfo, Identity identity, String identityName) {
        this.userInfo = userInfo;
        this.identity = identity;
        this.identityName = identityName;
    }
}