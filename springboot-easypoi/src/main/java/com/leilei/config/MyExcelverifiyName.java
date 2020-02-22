package com.leilei.config;

import cn.afterturn.easypoi.excel.entity.result.ExcelVerifyHandlerResult;

import cn.afterturn.easypoi.handler.inter.IExcelVerifyHandler;
import com.leilei.entity.UserVo;
import com.leilei.service.ICheckUserVoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author : leilei
 * @date : 15:36 2020/2/22
 * @desc :自定义excel校验名字是否重复  实现IExcelVerifyHandler接口
 */

@Component
public class MyExcelverifiyName implements IExcelVerifyHandler<UserVo> {
    @Autowired
    private ICheckUserVoService checkUserVoService;

    @Override
    public ExcelVerifyHandlerResult verifyHandler(UserVo userVo) {
        //设置默认验证为true
        ExcelVerifyHandlerResult excelVerifyHandlerResult = new ExcelVerifyHandlerResult(true);
        if (StringUtils.isNotBlank(userVo.getUsername())) {
            UserVo one = checkUserVoService.findOneByname(userVo.getUsername());
            //查询不为空则说明数据库中用户已存在，此次录入为重复录入
            if (one != null) {
                excelVerifyHandlerResult.setSuccess(false);
                excelVerifyHandlerResult.setMsg("对不起，此用户已存在，请不要重复提交");
            }
        }
        return excelVerifyHandlerResult;
    }
}
