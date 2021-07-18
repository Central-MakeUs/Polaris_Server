package com.example.dobit.src.dontHabitCheck.models;

import com.example.dobit.config.BaseEntity;
import com.example.dobit.src.dontHabit.models.DontHabit;
import com.example.dobit.src.user.models.UserInfo;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;


@NoArgsConstructor(access = AccessLevel.PUBLIC)
@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name = "DontHabitCheck")
public class DontHabitCheck extends BaseEntity {
    /**
     * idx
     */
    @Id
    @Column(name = "dnhCheckIdx", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dnhCheckIdx;

    /**
     * Dont 습관 idx
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dnhIdx", nullable = false)
    private DontHabit dontHabit;

    /**
     * 유저 idx
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userIdx", nullable = false)
    private UserInfo userInfo;

    @CreationTimestamp
    @Column(name = "checkDate", nullable = false, updatable = false)
    private Date checkDate;

    /**
     * 상태
     */
    @Column(name = "status", nullable = false, length = 10)
    private String status = "ACTIVE";


    public DontHabitCheck(DontHabit dontHabit, UserInfo userInfo) {
        this.dontHabit = dontHabit;
        this.userInfo = userInfo;
    }
}