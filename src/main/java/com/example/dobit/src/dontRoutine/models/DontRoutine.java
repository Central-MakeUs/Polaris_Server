package com.example.dobit.src.dontRoutine.models;

import com.example.dobit.config.BaseEntity;
import com.example.dobit.src.dontHabit.models.DontHabit;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@NoArgsConstructor(access = AccessLevel.PUBLIC)
@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name = "DontRoutine")
public class DontRoutine extends BaseEntity {
    /**
     * Dont 제거루틴 idx
     */
    @Id
    @Column(name = "dnRoutineIdx", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dnRoutineIdx;

    /**
     * Dont 제거루틴 내용
     */
    @Column(name = "dnRoutineContent", nullable = false, length = 45)
    private String dnRoutineContent;

    /**
     * Dont 습관 idx
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dnhIdx", nullable = false)
    private DontHabit dontHabit;

    /**
     * 상태
     */
    @Column(name = "status", nullable = false, length = 10)
    private String status = "ACTIVE";


    public DontRoutine(String dnRoutineContent, DontHabit dontHabit) {
        this.dnRoutineContent = dnRoutineContent;
        this.dontHabit = dontHabit;
    }
}