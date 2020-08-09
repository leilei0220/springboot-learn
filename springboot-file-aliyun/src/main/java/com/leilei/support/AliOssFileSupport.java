package com.leilei.support;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;
import com.leilei.config.AliYunOssConfig;
import com.leilei.config.CommonException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class AliOssFileSupport {
    @Autowired
    AliYunOssConfig aliYunOssConfig;

    /**
     * 文件上传
     *
     * @param file
     * @return
     * @throws Exception
     */
    public String upload(MultipartFile file) throws Exception {
        String bucketName = aliYunOssConfig.getBucketName();
        String uploadUrl = "";
        OSSClient ossClient = null;
        try {
            //创建oss实例
            ossClient = new OSSClient(aliYunOssConfig.getEndpoint(),
                    aliYunOssConfig.getAccessKeyId(), aliYunOssConfig.getAccessKeySecret());
            if (!ossClient.doesBucketExist(bucketName)) {
                //创建bucket
                ossClient.createBucket(bucketName);
                //设置bucket的访问权限：公共读
                ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
            }
            //获取上传文件流
            InputStream inputStream = file.getInputStream();
            //构建日期路径
            String filePath = new DateTime().toString("yyyy/MM/dd");
            ;
            //文件名：uuid.扩展名
            String original = file.getOriginalFilename();
            String fileName = null;
            String originalFilename = file.getOriginalFilename();
            log.info("上传原始文件名：{}", originalFilename);
            String fileType = original.substring(original.lastIndexOf("."));
            if (isContainChinese(originalFilename)) {
                fileName = UUID.randomUUID().toString();
            } else {
                fileName = StrUtil.removeSuffix(originalFilename, fileType);
                log.info("随机生成文件名：{}", fileName);
            }
            String newName = fileName + fileType;
            String fileUrl = buildOssKey(aliYunOssConfig.getFileHost(), filePath, newName);
            //文件上传至阿里云
            ossClient.putObject(bucketName, fileUrl, inputStream);
            //获取url地址
            uploadUrl = "https://" + aliYunOssConfig.getBucketName() + "." + aliYunOssConfig.getEndpoint() + "/" + fileUrl;
        } catch (IOException e) {
            throw new CommonException("文件上传失败");
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return uploadUrl;
    }

    public void logDownload(String name, HttpServletResponse response) throws Exception {
        //构建Oss对象
        OSSClient ossClient = null;
        ossClient = new OSSClient(aliYunOssConfig.getEndpoint(),
                aliYunOssConfig.getAccessKeyId(), aliYunOssConfig.getAccessKeySecret());
        //设置响应头为下载
        response.setContentType("application/x-download");
        //设置下载的文件名
        response.addHeader("Content-Disposition", "attachment;fileName=" + name);
        response.setCharacterEncoding("UTF-8");
        //上传的路径开发中需要保持到数据库，显示连接到页面。
        String filePath = new DateTime().toString("yyyy/MM/dd");
        String osskey = buildOssKey(aliYunOssConfig.getFileHost(), filePath, name);
        OSSObject ossObject = ossClient.getObject(aliYunOssConfig.getBucketName(), osskey);
        InputStream is = ossObject.getObjectContent();
        BufferedInputStream bis = null;//定义缓冲流
        try {
            bis = new BufferedInputStream(is);//把流放入缓存流
            OutputStream os = response.getOutputStream();//定义输出流的响应流。
            byte[] buffer = new byte[1024];//定义一个字节数
            int len;//记录每次读入到cbuf数组中的字符的个数
            while ((len = is.read(buffer)) != -1) {//开始输出
                os.write(buffer, 0, len); //从数组中每次写出len个字符
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (ossClient != null) {
                // 关闭OSSClient。
                ossClient.shutdown();
            }
        }
    }

    /**
     * 文件删除
     *
     * @param filename
     * @return
     */
    public Boolean deleteFile(String filename) {
        String bucketName = aliYunOssConfig.getBucketName();
        String fileHost = aliYunOssConfig.getFileHost();

        //这里暂时写死嘛， 实际开发中 上传后 会将图片名 保存到数据库 到时候根据数据库取出的值拼接即可
        String filePath = new DateTime().toString("yyyy/MM/dd");
        OSSClient ossClient = null;
        try {
            //构建Oss对象
            ossClient = new OSSClient(aliYunOssConfig.getEndpoint(),
                    aliYunOssConfig.getAccessKeyId(), aliYunOssConfig.getAccessKeySecret());
            //删除目录中的文件 如果删除后目录无文件了，则目录也会被删除
            String deleteFilePath = buildOssKey(fileHost, filePath, filename);
            log.warn("正在删除文件，所在阿里云服务器文件完整路径为：{}", deleteFilePath);
            ossClient.deleteObject(bucketName, deleteFilePath);
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return true;
    }

    public List<OSSObjectSummary> findFileList() {
        // 设置最大个数。
        final int maxKeys =999;
        // 列举文件。
        OSSClient ossClient = null;
        try {
            ossClient = new OSSClient(aliYunOssConfig.getEndpoint(),
                    aliYunOssConfig.getAccessKeyId(), aliYunOssConfig.getAccessKeySecret());
            ObjectListing objectListing = ossClient.listObjects(new ListObjectsRequest(aliYunOssConfig.getBucketName()).withMaxKeys(maxKeys));
            List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
            return sums;
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }


    }

    /**
     * 构建操作阿里云Oss 的文件文件名
     *
     * @param fileHost
     * @param filePath
     * @param name
     * @return
     */
    public String buildOssKey(String fileHost, String filePath, String name) {

        String osskey = fileHost + "/" + filePath + "/" + name;
        return osskey;
    }

    /**
     * 判断字符串中是否包含中文
     *
     * @param str 待校验字符串
     * @return 是否为中文
     * @warn 不能校验是否为中文标点符号
     */
    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }
}