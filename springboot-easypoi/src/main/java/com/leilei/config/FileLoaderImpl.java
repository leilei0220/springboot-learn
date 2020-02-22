package com.leilei.config;

import cn.afterturn.easypoi.cache.manager.IFileLoader;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author leilei
 * @date 2020/02/22
 * @desc 文件加载读取类, 根据路径加载指定文件   参照官网修改
 */
@Slf4j
public class FileLoaderImpl implements IFileLoader {


    @Override
    public byte[] getFile(String url) {
        InputStream fileis = null;
        ByteArrayOutputStream baos = null;
        try {

            //判断是否是网络地址
            if (url.startsWith("http")) {
                URL urlObj = new URL(url);
                URLConnection urlConnection = urlObj.openConnection();
                urlConnection.setConnectTimeout(30);
                urlConnection.setReadTimeout(60);
                urlConnection.setDoInput(true);
                fileis = urlConnection.getInputStream();
            } else {
                //先用绝对路径查询,再查询相对路径
                try {
                    fileis = new FileInputStream(url);
                } catch (FileNotFoundException e) {
                    //获取项目文件
                    fileis = FileLoaderImpl.class.getClassLoader().getResourceAsStream(url);
                    if (fileis == null) {
                        fileis = FileLoaderImpl.class.getResourceAsStream(url);
                    }
                }
            }
            baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fileis.read(buffer)) > -1) {
                baos.write(buffer, 0, len);
            }
            baos.flush();
            return baos.toByteArray();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(fileis);
            IOUtils.closeQuietly(baos);
        }
        log.error(fileis + "这个路径文件没有找到,请查询");
        return null;
    }
}