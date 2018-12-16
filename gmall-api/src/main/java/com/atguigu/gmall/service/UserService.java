package com.atguigu.gmall.service;

import com.atguigu.gmall.bean.UserAddress;
import com.atguigu.gmall.bean.UserInfo;

import java.util.List;

public interface UserService {

    List<UserInfo> queryUserInfoAll();

    UserInfo queryUserInfoById(String id);

    void updateUserInfoById(UserInfo userInfo);

    void removeUserInfoById(UserInfo userInfo);

    void saveUserInfo(UserInfo userInfo);

    List<UserAddress> queryUserAddressList();

    UserAddress queryUserAddressById(String id);

    void updateUserAddressById(UserAddress userAddress);

    void removeUserAddressById(UserAddress userAddress);

    void saveUserAddress(UserAddress userAddress);

    UserInfo queryUserInfoByLoginNameAndPasswd(String loginName, String passwd);

    List<UserAddress> queryUserAddressListByUserId(String userId);

}
