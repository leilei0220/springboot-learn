package com.leilei.controller;

import com.leilei.service.VehiclePublishService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lei
 * @create 2022-11-27 19:16
 * @desc
 **/
@RestController
@RequestMapping("/vehicle")
@RequiredArgsConstructor
public class VehicleRegisterController {
    private final VehiclePublishService publishService;

    @GetMapping("/register/{id}")
    public void register(@PathVariable("id") Integer id) {
        publishService.pushVehicleRegister(id);
    }
}
