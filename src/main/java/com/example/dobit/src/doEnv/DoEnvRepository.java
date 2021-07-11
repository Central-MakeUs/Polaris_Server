package com.example.dobit.src.doEnv;

import com.example.dobit.src.doEnv.models.DoEnv;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoEnvRepository extends CrudRepository<DoEnv, Integer> {
}