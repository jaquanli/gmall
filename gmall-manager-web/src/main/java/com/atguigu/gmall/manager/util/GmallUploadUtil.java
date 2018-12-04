package com.atguigu.gmall.manager.util;

import org.apache.commons.lang3.StringUtils;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class GmallUploadUtil {

    public static String fileUpload(MultipartFile multipartFile,String fileUrl){

        //读取配置文件中的内容
        String confPath = GmallUploadUtil.class.getResource("/track.conf").getFile();
        //初始化
        try {
            ClientGlobal.init(confPath);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        //创建Track客户端对象
        TrackerClient trackerClient = new TrackerClient();
        //获取track客户端服务连接
        TrackerServer connection = null;
        try {
            connection = trackerClient.getConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //根据连接创建上传客户端对象
        StorageClient storageClient = new StorageClient(connection,null);
        //获取数据的元数据
        String originalFilename = multipartFile.getOriginalFilename();
        //进行截取，获得扩展名
        String extName = StringUtils.substringAfterLast(originalFilename, ".");
        //进行上传，并返回包含文件路径的数组对象
        String[] uploadFile = new String[0];
        try {
            uploadFile = storageClient.upload_file(multipartFile.getBytes(), extName, null);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        //遍历出文件对象
        for (int i = 0; i < uploadFile.length; i++) {
            String path = uploadFile[i];
            fileUrl += "/" +path;

        }
        //返回带有连接的文件远程地址
        return fileUrl;


    }


}
