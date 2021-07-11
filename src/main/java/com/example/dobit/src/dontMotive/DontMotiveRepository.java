package com.example.dobit.src.dontMotive;

import com.example.dobit.src.dontHabit.models.DontHabit;
import com.example.dobit.src.dontMotive.models.DontMotive;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DontMotiveRepository extends CrudRepository<DontMotive, Integer> {
    List<DontMotive> findByDontHabitAndStatus(DontHabit dontHabit, String active);
}