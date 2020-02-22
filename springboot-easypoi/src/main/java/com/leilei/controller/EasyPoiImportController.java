package com.leilei.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import cn.afterturn.easypoi.handler.inter.IExcelVerifyHandler;
import com.leilei.entity.User;
import com.leilei.entity.UserVo;
import com.leilei.util.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author : leilei
 * @date : 15:13 2020/2/22
 * @desc : EasyPoi  excel 导入
 */
@RestController
@Slf4j
@RequestMapping("import")
public class EasyPoiImportController {

    @Autowired
    private IExcelVerifyHandler excelVerifyHandler;

    /**
     * 普通导入
     *
     * @param file
     */
    @PostMapping("/import1")
    public Object importExcel(MultipartFile file) throws IOException {
        List<User> users = ExcelUtils.importExcel(file, User.class);
        users.forEach(e -> System.out.println(e));
        return users;
    }

    /**
     * 文件校验导入
     *
     * @return
     */
    @RequestMapping("/import2")
    public Object upload(MultipartFile file,HttpServletResponse resp) throws Exception {
        //导入的基本配置
        ImportParams params = new ImportParams();
        //表头一行
        params.setHeadRows(1);
        //标题一行
        params.setTitleRows(1);
        //代表导入这里是需要验证的（根据字段上的注解）
        params.setNeedVerify(true);
        //设及一个自定义校验 （自定义校验名字不可重复）
        params.setVerifyHandler(excelVerifyHandler);
        //使用框架自身导入工具
        ExcelImportResult<UserVo> result = ExcelImportUtil.importExcelMore(file.getInputStream(), UserVo.class, params);
        //导入成功的数据
        List<UserVo> list = result.getList();
        //失败结果集
        List<UserVo> failList = result.getFailList();
        //拿到导出失败的工作簿
        Workbook failWorkbook = result.getFailWorkbook();
        //验证是否有失败的数据
        if (result.isVerifyFail()) {
            ServletOutputStream fos = resp.getOutputStream();
            //mime类型
            resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            resp.setHeader("Content-disposition", "attachment;filename=error.xlsx");
            result.getFailWorkbook().write(fos);
            fos.close();
        }
        return failList;
    }
}
