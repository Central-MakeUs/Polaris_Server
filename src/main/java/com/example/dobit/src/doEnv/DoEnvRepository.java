package com.example.dobit.src.doEnv;

import com.example.dobit.src.doEnv.models.DoEnv;
import com.example.dobit.src.doHabit.models.DoHabit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoEnvRepository extends CrudRepository<DoEnv, Integer> {
    List<DoEnv> findByDoHabitAndStatus(DoHabit doHabit, String active);
}