package com.leilei;

// import cn.hutool.http.HttpRequest;
// import cn.hutool.http.HttpUtil;
import io.github.pigmesh.ai.deepseek.core.DeepSeekClient;
import io.github.pigmesh.ai.deepseek.core.SyncOrAsyncOrStreaming;
import io.github.pigmesh.ai.deepseek.core.chat.ChatCompletionResponse;
import jakarta.servlet.http.HttpServletResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * @author: lei
 * @date: 2025-02-25 15:58
 * @desc:  https://try.pig4cloud.com/sse.html
 */
@RestController
public class DeepSeekController {



    @Autowired
    private DeepSeekClient deepSeekClient;



    @Value("${deepseek.api-key}")
    private String apiKey;

    @CrossOrigin("*")
    @GetMapping(value = "/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ChatCompletionResponse> chat(String prompt, HttpServletResponse response) {
        // 关键：设置字符编码为UTF-8
        response.setHeader("Content-Type", "text/event-stream;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Connection", "keep-alive");

        return deepSeekClient.chatFluxCompletion(prompt);
    }


    @CrossOrigin("*")
    @GetMapping(value = "/deepseek/models")
    public String listModel() throws IOException {
        OkHttpClient httpClient = new OkHttpClient();
        String url = "https://api.deepseek.com/models";
        Request getRequest = new Request.Builder()
                .url(url)
                .get()
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("Accept", "application/json")
                .build();
        Response response = httpClient.newCall(getRequest).execute();
        String string = response.body().string();
        return string;
    }

}
