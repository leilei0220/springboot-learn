package com.leilei;

import lombok.Data;

/**
 * @author lei
 * @create 2022-09-29 10:27
 * @desc 远程表单
 **/
@Data
public class RemoteForm {

    private String host;
    private String username;
    private String password;
    private String cmd;
}
