package com.example.dobit.src.doElse;

import com.example.dobit.src.doElse.models.DoElse;
import com.example.dobit.src.doHabit.models.DoHabit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DoElseRepository extends CrudRepository<DoElse, Integer> {
    List<DoElse> findByDoHabitAndStatus(DoHabit doHabit, String active);
}