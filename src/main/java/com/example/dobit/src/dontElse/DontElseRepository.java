package com.example.dobit.src.dontElse;

import com.example.dobit.src.dontElse.models.DontElse;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DontElseRepository extends CrudRepository<DontElse, Integer> {
}