package com.example.dobit.src.doNext;

import com.example.dobit.src.doHabit.models.DoHabit;
import com.example.dobit.src.doNext.models.DoNext;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DoNextRepository extends CrudRepository<DoNext, Integer> {
    List<DoNext> findByDoHabitAndStatus(DoHabit doHabit, String active);
}