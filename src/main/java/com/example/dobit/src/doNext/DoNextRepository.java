package com.example.dobit.src.doNext;

import com.example.dobit.src.doNext.models.DoNext;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DoNextRepository extends CrudRepository<DoNext, Integer> {
}