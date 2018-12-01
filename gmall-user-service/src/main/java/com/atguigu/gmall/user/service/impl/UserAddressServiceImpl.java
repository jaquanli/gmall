package com.atguigu.gmall.user.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.bean.UserAddress;
import com.atguigu.gmall.service.UserAddressService;
import com.atguigu.gmall.user.mapper.UserAddressMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class UserAddressServiceImpl implements UserAddressService {

    @Autowired
    private UserAddressMapper userAddressMapper;

    @Override
    public List<UserAddress> queryUserAddressList() {

        return userAddressMapper.selectAll();
    }

    @Override
    public UserAddress queryUserAddressById(String id) {

        return userAddressMapper.selectByPrimaryKey(Long.parseLong(id));
    }

    @Override
    public void updateUserAddressById(UserAddress userAddress) {
        userAddressMapper.updateByPrimaryKey(userAddress);
    }

    @Override
    public void removeUserAddressById(UserAddress userAddress) {

        userAddressMapper.delete(userAddress);

    }

    @Override
    public void saveUserAddress(UserAddress userAddress) {
        userAddressMapper.insert(userAddress);
    }


}
