package com.example.dobit.src.userToIdentity.models;

import com.example.dobit.config.BaseEntity;
import com.example.dobit.src.identity.models.Identity;
import com.example.dobit.src.user.models.UserInfo;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@NoArgsConstructor(access = AccessLevel.PUBLIC)
@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name = "UserToIdentity")
public class UserToIdentity extends BaseEntity {
    /**
     * idx
     */
    @Id
    @Column(name = "utiIdx", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int utiIdx;

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