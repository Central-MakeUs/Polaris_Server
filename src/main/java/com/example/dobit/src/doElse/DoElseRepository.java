package com.example.dobit.src.doElse;

import com.example.dobit.src.doElse.models.DoElse;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DoElseRepository extends CrudRepository<DoElse, Integer> {
}