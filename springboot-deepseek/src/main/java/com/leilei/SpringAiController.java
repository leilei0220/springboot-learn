package com.leilei;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: lei
 * @date: 2025-02-27 9:54
 * @desc:
 */
@RestController
@CrossOrigin(origins = "*")
@Slf4j
public class SpringAiController {

    private final ChatClient chatClient;

    public SpringAiController(ChatClient.Builder builder) {
        this.chatClient = builder.defaultSystem("你是一个天气预报员，当有人输入城市,日期的时候，你输出对应的天气预报信息，" +
                "生成结果在html页面中以markdown的格式输出，最后输出结尾的时候始终以下面的语句结尾：感谢您的咨询，我是LEI-AI助手。").build();
    }

    @GetMapping(value = "/weather/chat/{message}")
    public String chat(@PathVariable("message")String message) {
        return chatClient.prompt()
                .user(message)
                .call()
                .content();
    }
}
