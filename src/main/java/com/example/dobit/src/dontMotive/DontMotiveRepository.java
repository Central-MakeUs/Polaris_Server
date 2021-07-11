package com.example.dobit.src.dontMotive;

import com.example.dobit.src.dontMotive.models.DontMotive;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DontMotiveRepository extends CrudRepository<DontMotive, Integer> {
}