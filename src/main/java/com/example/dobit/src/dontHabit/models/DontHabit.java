package com.example.dobit.src.dontHabit.models;

import com.example.dobit.config.BaseEntity;
import com.example.dobit.src.dontElse.models.DontElse;
import com.example.dobit.src.dontMotive.models.DontMotive;
import com.example.dobit.src.dontRoutine.models.DontRoutine;
import com.example.dobit.src.userIdentity.models.UserIdentity;
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
@Table(name = "DontHabit")
public class DontHabit extends BaseEntity {
    /**
     * Dont 습관 idx
     */
    @Id
    @Column(name = "dnhIdx", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dnhIdx;

    /**
     * Dont 습관명
     */
    @Column(name = "dnhName", nullable = false, length = 45)
    private String dnhName;

    /**
     * Dont 습관 하지않았을때 이점
     */
    @Column(name = "dnhAdvantage", nullable = false, length = 45)
    private String dnhAdvantage;

    /**
     * Dont 습관 환경
     */
    @Column(name = "dnhEnv", nullable = false, length = 45)
    private String dnhEnv;


    /**
     * 유저 정체성 인덱스
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userIdentityIdx", nullable = false)
    private UserIdentity userIdentity;

    /**
     * 상태
     */
    @Column(name = "status", nullable = false, length = 10)
    private String status = "ACTIVE";


    @OneToMany(mappedBy = "dontHabit", cascade = CascadeType.ALL)
    private List<DontRoutine> dontRoutineList = new ArrayList<>();

    @OneToMany(mappedBy = "dontHabit", cascade = CascadeType.ALL)
    private List<DontMotive> dontMotiveList = new ArrayList<>();

    @OneToMany(mappedBy = "dontHabit", cascade = CascadeType.ALL)
    private List<DontElse> dontElseList = new ArrayList<>();




    public DontHabit(String dnhName, String dnhAdvantage, String dnhEnv,UserIdentity userIdentity) {
        this.dnhName = dnhName;
        this.dnhAdvantage = dnhAdvantage;
        this.dnhEnv = dnhEnv;
        this.userIdentity = userIdentity;
    }
}