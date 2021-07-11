package com.example.dobit.src.doRoutine;

import com.example.dobit.src.doRoutine.models.DoRoutine;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DoRoutineRepository extends CrudRepository<DoRoutine, Integer> {
}