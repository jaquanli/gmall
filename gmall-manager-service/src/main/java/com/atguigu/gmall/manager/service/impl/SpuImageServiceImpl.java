package com.atguigu.gmall.manager.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.bean.SpuImage;
import com.atguigu.gmall.manager.mapper.SpuImageMapper;
import com.atguigu.gmall.service.SpuImageService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class SpuImageServiceImpl implements SpuImageService {

    @Autowired
    private SpuImageMapper spuImageMapper;

    @Override
    public List<SpuImage> querySpuImageListBySpuId(String spuId) {

        SpuImage spuImage = new SpuImage();
        spuImage.setSpuId(Long.parseLong(spuId));

        return spuImageMapper.select(spuImage);
    }
}
