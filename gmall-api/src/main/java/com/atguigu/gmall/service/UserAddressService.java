package com.atguigu.gmall.service;

import com.atguigu.gmall.bean.UserAddress;

import java.util.List;

public interface UserAddressService {

    List<UserAddress> queryUserAddressList();

    UserAddress queryUserAddressById(String id);

    void updateUserAddressById(UserAddress userAddress);

    void removeUserAddressById(UserAddress userAddress);

    void saveUserAddress(UserAddress userAddress);
}
