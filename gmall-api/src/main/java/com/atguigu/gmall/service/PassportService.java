package com.atguigu.gmall.service;

public interface PassportService {
    void sendMergeCartMessage(Long userId, String cartCookieValue);

    void sendFlushCacheFormCartInfoMessage(Long id);
}
