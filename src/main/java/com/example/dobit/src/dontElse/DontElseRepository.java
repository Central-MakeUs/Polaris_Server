package com.example.dobit.src.dontElse;

import com.example.dobit.src.dontElse.models.DontElse;
import com.example.dobit.src.dontHabit.models.DontHabit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DontElseRepository extends CrudRepository<DontElse, Integer> {
    List<DontElse> findByDontHabitAndStatus(DontHabit dontHabit, String active);
}