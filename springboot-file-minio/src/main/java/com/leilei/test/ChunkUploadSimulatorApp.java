package com.leilei.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class ChunkUploadSimulatorApp implements CommandLineRunner {

    @Autowired
    private ChunkUploader uploader;

    public static void main(String[] args) {
        SpringApplication.run(ChunkUploadSimulatorApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // 替换为你的本地测试大文件路径
        File file = new File("F:/google_down/_eamp-business-service_logs (3).txt");
        if (!file.exists()) {
            System.err.println("文件不存在");
            return;
        }
        uploader.uploadInChunks(file, 5); // 分片大小 MB
    }
}
