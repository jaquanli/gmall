package com.atguigu.gmall.manager.util;

import org.apache.commons.lang3.StringUtils;
import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class GmallUploadUtil {

    public static String fileUpload(MultipartFile multipartFile,String fileUrl){


        try {
            //读取配置文件中的内容
            String confPath = GmallUploadUtil.class.getResource("/track.conf").getFile();
            //初始化

            ClientGlobal.init(confPath);

            //创建Track客户端对象
            TrackerClient trackerClient = new TrackerClient();
            //获取track客户端服务连接
            TrackerServer connection = null;

            connection = trackerClient.getConnection();

            //storageServer.
            //根据连接创建上传客户端对象
            StorageClient storageClient = new StorageClient(connection, null);
            //获取数据的元数据
            String originalFilename = multipartFile.getOriginalFilename();
            //进行截取，获得扩展名
            String extName = StringUtils.substringAfterLast(originalFilename, ".");
            //进行上传，并返回包含文件路径的数组对象
            String[] uploadFile = new String[0];

            uploadFile = storageClient.upload_file(multipartFile.getBytes(), extName, null);


            //遍历出文件对象
            StringBuilder fileUrlBuilder = new StringBuilder(fileUrl);
            for (String path : uploadFile) {
                fileUrlBuilder.append("/").append(path);

            }
            fileUrl = fileUrlBuilder.toString();
        } catch (IOException | MyException e) {
            e.printStackTrace();
        }
        //返回带有连接的文件远程地址
        return fileUrl;


    }


}
