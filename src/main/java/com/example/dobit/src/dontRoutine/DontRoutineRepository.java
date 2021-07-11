package com.example.dobit.src.dontRoutine;

import com.example.dobit.src.dontRoutine.models.DontRoutine;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DontRoutineRepository extends CrudRepository<DontRoutine, Integer> {
}