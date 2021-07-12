package com.example.dobit.src.doHabit;

import com.example.dobit.src.doHabit.models.DoHabit;
import com.example.dobit.src.userIdentity.models.UserIdentity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface DoHabitRepository extends CrudRepository<DoHabit, Integer> {
    DoHabit findByDhIdxAndStatus(Integer dhIdx, String active);


    Boolean existsByUserIdentityAndStatus(UserIdentity userIdentity, String active);

    DoHabit findByUserIdentityAndStatus(UserIdentity userIdentity, String active);
}