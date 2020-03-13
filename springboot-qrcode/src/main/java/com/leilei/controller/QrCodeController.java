package com.leilei.controller;


import com.leilei.util.QrCodeUtil;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author leilei
 * @version 1.0
 * @date 2020/3/12 12:38
 * @desc: 获取二维码
 */
@Slf4j
@RestController
@RequestMapping("/qrCode")
public class QrCodeController {


    /**
     * @url:  http://127.0.0.1:8080/qrCode/getQrCode?url=https://www.baidu.com
     */
    @GetMapping(value = "/getQrCode")
    public void getCommonQRCode(HttpServletResponse response, String url) throws Exception {
        ServletOutputStream stream = null;
        try {
            stream = response.getOutputStream();
            //使用工具类生成不带logo二维码
            QrCodeUtil.encode(url, stream);
        } finally {
            //关闭流操作
            if (stream != null) {
                stream.flush();
                stream.close();
            }
        }
    }

    /**
     * @url : http://127.0.0.1:8080/qrCode/getQrCodeByLogo?url=https://www.taobao.com
     */
    @GetMapping(value = "/getQrCodeByLogo")
    public void getQRCodeWithLogo(HttpServletResponse response, String url) throws Exception {
        ServletOutputStream stream = null;
        try {
            stream = response.getOutputStream();
            //当前是获取本地logo图片地址  也可以修改方法获取网络中图片
            String  logoPath= ResourceUtils.getURL("classpath:").getPath()+"static/images/"+"logo.jpg";
            log.info("logo完整url地址：{}", logoPath);
            //使用工具类生成带logo二维码
            QrCodeUtil.encode(url, logoPath, stream, true);
        } finally {
            //关闭流操作
            if (stream != null) {
                stream.flush();
                stream.close();
            }
        }
    }
}
