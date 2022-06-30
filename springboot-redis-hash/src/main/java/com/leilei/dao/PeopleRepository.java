package com.leilei.dao;

import com.leilei.entity.People;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author lei
 * @create 2022-04-25 11:35
 * @desc
 **/
@Repository
public interface PeopleRepository extends CrudRepository<People, Integer> {

    /**
     * 根据名字查询用户
     * @param name
     * @return
     */
    Optional<People> findPeopleByName(String name);

}
