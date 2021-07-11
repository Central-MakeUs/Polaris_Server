package com.example.dobit.src.dontHabit;

import com.example.dobit.src.dontHabit.models.DontHabit;
import com.example.dobit.src.userIdentity.models.UserIdentity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DontHabitRepository extends CrudRepository<DontHabit, Integer> {

    Boolean existsByUserIdentityAndStatus(UserIdentity userIdentity, String active);

    DontHabit findByDnhIdxAndStatus(Integer dnhIdx, String active);
}
 