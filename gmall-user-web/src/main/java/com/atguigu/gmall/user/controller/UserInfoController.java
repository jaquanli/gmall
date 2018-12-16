package com.atguigu.gmall.user.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.ResultEntity;
import com.atguigu.gmall.bean.UserInfo;
import com.atguigu.gmall.service.UserService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserInfoController {

    @Reference
    private UserService userService;

    @RequestMapping("get/user/info/list")
    public List<UserInfo> getUserInfoList() {

        return userService.queryUserInfoAll();
    }

    @RequestMapping("get/user/info/{userInfoId}")
    public ResultEntity<UserInfo> getUserInfoById(@PathVariable("userInfoId") String userInfoId) {

        ResultEntity<UserInfo> resultEntity = new ResultEntity<>();
        try {

            UserInfo userInfo = userService.queryUserInfoById(userInfoId);

            resultEntity.setResult(ResultEntity.SUCCESS);
            resultEntity.setDate(userInfo);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setResult(ResultEntity.FAILED);
            resultEntity.setMassage(e.getMessage());
        }
        return resultEntity;
    }


    @RequestMapping("modify/user/info")
    public ResultEntity<String> updateUserInfo(UserInfo userInfo) {
        //创建封装结果的实体类
        ResultEntity<String> resultEntity = new ResultEntity<>();
        //进行try-catch
        try {
            //调用服务更新
            userService.updateUserInfoById(userInfo);
            //将成功的结果返回
            resultEntity.setResult(ResultEntity.SUCCESS);
            //返回消息
            resultEntity.setMassage("更新成功");
        } catch (Exception e) {
            e.printStackTrace();
            //将失败的结果返回
            resultEntity.setResult(ResultEntity.FAILED);
            //将异常信息返回
            resultEntity.setMassage(e.getMessage());
        }
        //返回封装好的数据
        return resultEntity;
    }

    @RequestMapping("reduce/user/info/{userInfoId}")
    public ResultEntity<String> reduceUserInfo(@PathVariable("userInfoId") String userInfoId) {
        //创建封装结果的实体类
        ResultEntity<String> resultEntity = new ResultEntity<>();
        //进行try-catch
        try {
            //创建UserAddress对象用作封装数据
            UserInfo userInfo = new UserInfo();
            //将获得的参数封装到对象中
            userInfo.setId(Long.parseLong(userInfoId));
            //调用服务进行删除
            userService.removeUserInfoById(userInfo);
            //将成功的结果返回
            resultEntity.setResult(ResultEntity.SUCCESS);
            //将成功的消息封装到结果
            resultEntity.setMassage("删除成功");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            //将失败的结果返回
            resultEntity.setResult(ResultEntity.FAILED);
            //将异常信息返回
            resultEntity.setMassage(e.getMessage());
        }
        //返回结果
        return resultEntity;
    }

    @RequestMapping("add/user/info")
    public ResultEntity<String> addUserInfo(UserInfo userInfo) {
        ResultEntity<String> resultEntity = new ResultEntity<>();
        try {
            userService.saveUserInfo(userInfo);
            //将成功的结果返回
            resultEntity.setResult(ResultEntity.SUCCESS);
            //将成功的消息封装到结果
            resultEntity.setMassage("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            //将失败的结果返回
            resultEntity.setResult(ResultEntity.FAILED);
            //将异常信息返回
            resultEntity.setMassage(e.getMessage());
        }
        //返回结果
        return resultEntity;
    }


}
