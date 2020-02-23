package com.leilei.controller;


import com.leilei.util.FastDfsUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


/**
 * @author lei
 * @version 1.0
 * @date 2020/01/08 17:26
 * @desc: FastDfs  Controller
 */
@RestController
@Slf4j
public class UploadController {
    /**
     * fastDFS服务器地址
     */
    @Value("${app.base.url}")
    private String appUrl;

    /**
     * 访问上传页面
     * @return
     */
    @GetMapping("/index")
    public Object index(){
        return new ModelAndView("index");
    }

    /**
     * 单文件上传
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/upload")
    public Object upload(MultipartFile file) throws Exception {
        ModelAndView view = new ModelAndView("success");
        String uploadFile = FastDfsUtils.upload(file);
        String imgUrl = appUrl + uploadFile;
        log.info("[fastdfs服务器文件路径] = [{}]", imgUrl);
        view.addObject("imgUrl", imgUrl);
        return view;
    }

    /**
     * 文件删除
     * @return
     */
    @DeleteMapping("/rmfastdfs")
    public String delete(String fileId){
        //    /group1/M00/00/01/rBAEtF1ESveAJIrtAATb0rfoOcM166.jpg
        try {
//            fileId = fileId.substring(1);
//            String groupName = fileId.substring(0,fileId.indexOf("/"));
//            log.info("[组名] - [{}]", groupName);
//            String fileName = fileId.substring(fileId.indexOf("/")+1);
//            log.info("[文件名] - [{}]", fileName);
            FastDfsUtils.delete(fileId);
            return "成功!";
        } catch (Exception e) {
            e.printStackTrace();
            return "删除失败!"+e.getMessage();
        }

    }

}
