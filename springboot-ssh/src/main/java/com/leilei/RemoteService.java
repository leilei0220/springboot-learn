package com.leilei;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import lombok.extern.log4j.Log4j2;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * @author lei
 * @create 2022-09-29 10:38
 * @desc 远程服务
 **/
@Log4j2
public class RemoteService {

    public static void connRemote(RemoteForm form) {
        Connection conn = new Connection(form.getHost());
        Session session = null;
        try {
            conn.connect();
            boolean loginSuccessful = conn.authenticateWithPassword(form.getUsername(), form.getPassword());
            if (!loginSuccessful) {
                throw new RuntimeException("登录失败");
            }
            session = conn.openSession();
            session.execCommand(form.getCmd());
            InputStream stdout = new StreamGobbler(session.getStderr());
            BufferedReader stdoutReader = new BufferedReader(new InputStreamReader(stdout, StandardCharsets.UTF_8));
            String line;
            while ((line = stdoutReader.readLine()) != null) {
                log.info(line);
            }
            // 0－成功 非0－失败
            log.info("运行结果：{}", session.getExitStatus() == 0 ? "成功" : "失败");
        } catch (Exception e) {
            log.error("远程连接异常：{}", e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
            conn.close();
        }
    }

    public static void main(String[] args) {
        RemoteForm remoteForm = new RemoteForm();
        remoteForm.setHost("10.50.xxx.xxx");
        remoteForm.setUsername("root");
        remoteForm.setPassword("xxx");
        remoteForm.setCmd("docker logs --tail=3 1ce763bc98be");
        connRemote(remoteForm);
    }
}
