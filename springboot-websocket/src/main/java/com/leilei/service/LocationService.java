package com.leilei.service;

import com.leilei.entity.Location;
import org.springframework.stereotype.Service;

/**
 * @author lei
 * @create 2023-05-05 11:36
 * @desc
 **/
@Service
public class LocationService {


    public Location getLastLocationByUser(String userId) {
        return new Location(userId, 104444, 30441);
    }
}
