package com.example.dobit.src.userIdentity.models;


import com.example.dobit.config.BaseEntity;
import com.example.dobit.src.doHabit.models.DoHabit;
import com.example.dobit.src.doHabitCheck.models.DoHabitCheck;
import com.example.dobit.src.dontHabit.models.DontHabit;
import com.example.dobit.src.dontHabitCheck.models.DontHabitCheck;
import com.example.dobit.src.user.models.UserInfo;
import com.example.dobit.src.userIdentityColor.models.UserIdentityColor;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name = "UserIdentity")
public class UserIdentity extends BaseEntity {
    /**
     * 유저 정체성 idx
     */
    @Id
    @Column(name = "userIdentityIdx", nullable = false,updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userIdentityIdx;

    /**
     * 유저
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userIdx", nullable = false)
    private UserInfo userInfo;

    /**
     * 유저 정체성명
     */
    @Column(name = "userIdentityName",nullable = false,length = 45)
    private String userIdentityName;

    /**
     * 유저 정체성 컬러
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userIdentityColorIdx", nullable = false)
    private UserIdentityColor userIdentityColor;

    /**
     * 상태
     */
    @Column(name = "status", nullable = false, length = 10)
    private String status = "ACTIVE";


    @OneToMany(mappedBy = "userIdentity", cascade = CascadeType.ALL)
    private List<DoHabit> doHabits = new ArrayList<>();


    @OneToMany(mappedBy = "userIdentity", cascade = CascadeType.ALL)
    private List<DontHabit> dontHabits = new ArrayList<>();

    @OneToMany(mappedBy = "userIdentity", cascade = CascadeType.ALL)
    private List<DoHabitCheck> doHabitChecks = new ArrayList<>();

    @OneToMany(mappedBy = "userIdentity", cascade = CascadeType.ALL)
    private List<DontHabitCheck> dontHabitChecks = new ArrayList<>();

    public UserIdentity(UserInfo userInfo,String userIdentityName,UserIdentityColor userIdentityColor){
        this.userInfo = userInfo;
        this.userIdentityName = userIdentityName;
        this.userIdentityColor = userIdentityColor;
    }
}
