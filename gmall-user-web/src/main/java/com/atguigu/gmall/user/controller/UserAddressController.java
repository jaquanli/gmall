package com.atguigu.gmall.user.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.ResultEntity;
import com.atguigu.gmall.bean.UserAddress;
import com.atguigu.gmall.service.UserAddressService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserAddressController {

    @Reference
    private UserAddressService userAddressService;

    @RequestMapping("get/user/address/list")
    public List<UserAddress> getUserAddressList() {

        return userAddressService.queryUserAddressList();
    }

    @RequestMapping("get/user/address/{id}")
    public UserAddress getUserAddressById(@PathVariable("id") String id) {

        return userAddressService.queryUserAddressById(id);
    }

    @RequestMapping("modify/user/address")
    public ResultEntity<String> updateUserAddress(UserAddress userAddress) {
        //创建封装结果的实体类
        ResultEntity<String> resultEntity = new ResultEntity<>();
        //进行try-catch
        try {
            //调用服务更新
            userAddressService.updateUserAddressById(userAddress);
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

    @RequestMapping("reduce/user/address/{userAddressId}")
    public ResultEntity<String> reduceUserAddress(@PathVariable("userAddressId") String userAddressId) {
        //创建封装结果的实体类
        ResultEntity<String> resultEntity = new ResultEntity<>();
        //进行try-catch
        try {
            //创建UserAddress对象用作封装数据
            UserAddress userAddress = new UserAddress();
            //将获得的参数封装到对象中
            userAddress.setId(new Integer(userAddressId));
            //调用服务进行删除
            userAddressService.removeUserAddressById(userAddress);
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

    @RequestMapping("add/user/address")
    public ResultEntity<String> addUserAddress(UserAddress userAddress){
        ResultEntity<String> resultEntity = new ResultEntity<>();
        try {
            userAddressService.saveUserAddress(userAddress);
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
        return  resultEntity;
    }


}
