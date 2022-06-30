package com.leilei;

import com.leilei.dao.PeopleRepository;
import com.leilei.entity.People;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@SpringBootTest
public class SpringbootRedisHashApplicationTests {

    private final PeopleRepository peopleRepository;
    @Autowired
    public SpringbootRedisHashApplicationTests(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Test
    public void testSave() {
        People people = new People();
        people.setId(1);
        people.setName("王麻子");
        people.setExpiration(3000L);
        people.setPoint(new Point(103D,22D));
        people.setHobby(Arrays.asList("篮球","足球","乒乓"));
        peopleRepository.save(people);
    }

    @Test
    public void testSaveBatch() {
        List<People> peopleList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            People people = new People();
            people.setId(i + 1);
            people.setName(UUID.randomUUID().toString());
            people.setExpiration(3000L);
            peopleList.add(people);
        }
        peopleRepository.saveAll(peopleList);
    }


    @Test
    public void testGet() {
        peopleRepository.findById(1).ifPresent(System.out::println);
        peopleRepository.findPeopleByName("王麻子").ifPresent(System.out::println);
    }

}
