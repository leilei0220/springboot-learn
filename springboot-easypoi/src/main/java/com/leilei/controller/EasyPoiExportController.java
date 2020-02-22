package com.leilei.controller;

import cn.afterturn.easypoi.entity.vo.NormalExcelConstants;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.leilei.entity.DepartMent;
import com.leilei.entity.User;
import com.leilei.entity.UserVo;
import com.leilei.service.IEasyPoiService;
import com.leilei.util.ExcelUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : leilei
 * @date : 11:25 2020/2/22
 * @desc : EasyPoi  excel 导出
 */
@Controller
@Slf4j
public class EasyPoiExportController {

    @Autowired
    private IEasyPoiService easyPoiService;



    @RequestMapping("/poi")
    public String index() {
        return "poi";
    }


    /**
     * 导出
     */
    @RequestMapping("/outexcel")
    public String download(ModelMap map) {
        List<UserVo> list = easyPoiService.findUserVoList();

        //导出基本信息的配置
        ExportParams params = new ExportParams("用户列表", "用户", ExcelType.XSSF);
//        params.setFreezeCol(2);//冻结的列
        /**excel文件要导出的数据*/
        map.put(NormalExcelConstants.DATA_LIST, list);
        /**excel文件导出的实体*/
        map.put(NormalExcelConstants.CLASS, UserVo.class);
        /**excel文件参数*/
        map.put(NormalExcelConstants.PARAMS, params);
        /**excel文件名称*/
        map.put(NormalExcelConstants.FILE_NAME, "用户列表");
        return NormalExcelConstants.EASYPOI_EXCEL_VIEW;

    }

    /**
     * 导出
     *
     * @param response
     */
    @RequestMapping(value = "/export")
    public void exportExcel(HttpServletResponse response) throws IOException {

        List<User> users = easyPoiService.findUserList();
        ExcelUtils.exportExcel(users, "用户信息表", "用户信息", User.class, "用户信息", response);
    }
}
