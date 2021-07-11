package com.example.dobit.src.doRoutine;

import com.example.dobit.src.doHabit.models.DoHabit;
import com.example.dobit.src.doRoutine.models.DoRoutine;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DoRoutineRepository extends CrudRepository<DoRoutine, Integer> {
    List<DoRoutine> findByDoHabitAndStatus(DoHabit doHabit, String active);
}