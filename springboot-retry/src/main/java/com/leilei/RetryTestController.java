package com.leilei;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author: lei
 * @date: 2025-06-30 14:20
 * @desc:
 */
@RestController
@RequestMapping("/retry")
public class RetryTestController {

    private final ReTryTestService reTryTestService;

    public RetryTestController(ReTryTestService reTryTestService) {
        this.reTryTestService = reTryTestService;
    }

    @RequestMapping("/test")
    public String testReTry() {
        return reTryTestService.testReTry(UUID.randomUUID().toString());
    }
}
