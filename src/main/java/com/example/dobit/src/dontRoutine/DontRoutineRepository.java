package com.example.dobit.src.dontRoutine;

import com.example.dobit.src.dontHabit.models.DontHabit;
import com.example.dobit.src.dontRoutine.models.DontRoutine;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DontRoutineRepository extends CrudRepository<DontRoutine, Integer> {
    List<DontRoutine> findByDontHabitAndStatus(DontHabit dontHabit, String active);
}