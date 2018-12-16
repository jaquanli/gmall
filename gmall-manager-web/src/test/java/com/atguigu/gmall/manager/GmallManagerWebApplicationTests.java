package com.atguigu.gmall.manager;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GmallManagerWebApplicationTests {



    @Test
    public void contextLoads() throws IOException, MyException {

//        String path = GmallManagerWebApplicationTests.class.getResource("/track.conf").getFile();
//
//        ClientGlobal.init(path);
//
//        TrackerClient trackerClient = new TrackerClient();
//        TrackerServer connection = trackerClient.getConnection();
//
//        StorageClient storageClient = new StorageClient(connection,null);
//
//        String local_filename = "\\1.bmp";
//
//        String[] uploadFile = storageClient.upload_file(local_filename, "bmp", null);
//
//        for (String s : uploadFile) {
//            System.err.println(s);
//        }

    }

}
