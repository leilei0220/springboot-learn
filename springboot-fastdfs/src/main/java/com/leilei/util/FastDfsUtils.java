package com.leilei.util;

import lombok.extern.slf4j.Slf4j;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author lei
 * @version 1.0
 * @date 2020/01/08 17:06
 * @desc: FastDfsUtils
 */
@Slf4j
public class FastDfsUtils {
     
    public static String CONF_FILENAME  = FastDfsUtils.class.getResource("/fdfs_client.conf").getFile();

    /**
     * 上传文件
     * @param file
     * @return
     */
    public static  String upload(MultipartFile file) {

        log.info("[文件类型] - [{}]", file.getContentType());
        log.info("[文件大小] - [{}]", file.getSize());
        String originalFilename = file.getOriginalFilename();
        log.info("[原始文件名] - [{}]", originalFilename);
        String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        log.info("[文件扩展名] = [{}]", extName);
        try { 
            ClientGlobal.init(CONF_FILENAME);
 
            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getTrackerServer();
            StorageServer storageServer = null;
 
            StorageClient storageClient = new StorageClient(trackerServer, storageServer);
            NameValuePair[] nvp = null;
            String[] fileIds = storageClient.upload_file(file.getBytes(),extName,nvp);
            log.info("[组名] - [{}]", fileIds[0]);
            log.info("[路径] - [{}]", fileIds[1]);
            return  fileIds[0]+"/"+fileIds[1];
        } catch (Exception e) {
            e.printStackTrace();
            return  null;
        }
    }

    /**
     * 下载文件
     * 传入参数 组名+文件名：exmaple: /group1/M00/00/01/rBAEtF1ESveAJIrtAATb0rfoOcM166.jpg
     * @param fileId
     * @return
     */
    public static byte[] download(String fileId) {
        fileId = fileId.substring(1);
        String groupName = fileId.substring(0,fileId.indexOf("/"));
        log.info("[组名] - [{}]", groupName);
        String fileName = fileId.substring(fileId.indexOf("/")+1);
        log.info("[文件名] - [{}]", fileName);
        try {
 
            ClientGlobal.init(CONF_FILENAME);
            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getTrackerServer();
            StorageClient storageClient = new StorageClient(trackerServer);
            byte[] b = storageClient.download_file(groupName, fileName);
            return  b;
        } catch (Exception e) {
            e.printStackTrace();
            return  null;
        } 
    }

    /**
     *  删除文件的id 组名 +文件名
     *  传入参数 组名+文件名：exmaple: /group1/M00/00/01/rBAEtF1ESveAJIrtAATb0rfoOcM166.jpg
     * @param fileId
     */
    public static void delete(String fileId){
        fileId = fileId.substring(1);
        String groupName = fileId.substring(0,fileId.indexOf("/"));
        log.info("[组名] - [{}]", groupName);
        String fileName = fileId.substring(fileId.indexOf("/")+1);
        log.info("[文件名] - [{}]", fileName);
        try { 
            ClientGlobal.init(CONF_FILENAME);
 
            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getTrackerServer();
            StorageClient storageClient = new StorageClient(trackerServer);
            int i = storageClient.delete_file(groupName,fileName);
            System.out.println( i==0 ? "删除成功" : "删除失败:"+i);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new RuntimeException("删除异常,"+e.getMessage());
        } 
    }
}