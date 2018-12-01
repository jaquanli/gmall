package com.atguigu.gmall.service;

import com.atguigu.gmall.bean.UserInfo;

import java.util.List;

public interface UserInfoService {

    List<UserInfo> queryUserInfoAll();

    UserInfo queryUserInfoById(String id);

    void updateUserInfoById(UserInfo userInfo);

    void removeUserInfoById(UserInfo userInfo);

    void saveUserInfo(UserInfo userInfo);
}
