package com.example.dobit.src.doEnv.models;

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
@Table(name = "DoEnv")
public class DoEnv extends BaseEntity {
    /**
     * Do 환경 idx
     */
    @Id
    @Column(name = "dEnvIdx", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dEnvIdx;

    /**
     * Do 환경 내용
     */
    @Column(name = "dEnvContent", nullable = false, length = 45)
    private String dEnvContent;

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


    public DoEnv(String dEnvContent, DoHabit doHabit) {
        this.dEnvContent = dEnvContent;
        this.doHabit = doHabit;
    }
}