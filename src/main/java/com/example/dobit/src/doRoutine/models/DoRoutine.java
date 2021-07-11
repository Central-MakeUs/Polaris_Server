package com.example.dobit.src.doRoutine.models;

import com.example.dobit.config.BaseEntity;
import com.example.dobit.src.doHabit.models.DoHabit;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;



@NoArgsConstructor(access = AccessLevel.PUBLIC)
@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name = "DoRoutine")
public class DoRoutine extends BaseEntity {
    /**
     * Do 루틴 idx
     */
    @Id
    @Column(name = "dRoutineIdx", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dRoutineIdx;

    /**
     * Do 다음단계 내용
     */
    @Column(name = "dRoutineContent", nullable = false, length = 45)
    private String dRoutineContent;

    /**
     * Do 습관 idx
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dhIdx", nullable = false)
    private DoHabit doHabit;

    /**
     * 상태
     */
    @Column(name = "status", nullable = false, length = 10)
    private String status = "ACTIVE";


    public DoRoutine(String dRoutineContent, DoHabit doHabit) {
        this.dRoutineContent = dRoutineContent;
        this.doHabit = doHabit;
    }
}