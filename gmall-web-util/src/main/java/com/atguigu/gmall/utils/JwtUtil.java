package com.atguigu.gmall.utils;

import io.jsonwebtoken.*;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class JwtUtil {

    public static void main(String[] args) {
        Map<String,String> map = new HashMap<>();
        map.put("userId","12");
        map.put("nikeName","jaquanli");

        String atguiguToken = encode("atguigu", map, "192.168.131.131");
        System.err.println("加密结果" + atguiguToken);

        Map decode = decode("atguigu", atguiguToken, "192.168.131.131");
        String userId = (String) decode.get("userId");
        String nikeName = (String) decode.get("nikeName");
        System.err.println("解密结果userId：" + userId);
        System.err.println("解密结果nikeName：" + nikeName);

    }

    /**
     * 加密token
     * @param key 企业秘钥
     * @param param 要加密的参数，即私有方法
     * @param salt 盐值，即获取的每个用户访问时独有的信息
     * @return 加密后的token
     */
    public static String encode(String key, Map param,String salt){
        //先判断盐值是否存在
        if (StringUtils.isNotBlank(salt)) {
            //将盐值和秘钥拼接
            key += salt;
        }
        //进行加密
        JwtBuilder jwtBuilder = Jwts.builder().signWith(SignatureAlgorithm.HS256, key) ;
        //加入要加密的参数
        jwtBuilder.addClaims(param);
        //生成加密完成的后的token
        String token = jwtBuilder.compact();
        return token;
    }

    /**
     *
     * @param key 企业秘钥
     * @param token 要解密的token
     * @param salt 盐值，即获取的每个用户访问时独有的信息
     * @return 解密后的token信息，包含加密时参数的信息
     * @throws SignatureException 异常
     */
    public static Map decode(String key,String token,String salt) throws SignatureException {

        //先判断盐值是否存在
        if (StringUtils.isNotBlank(salt)) {
            //将盐值和秘钥拼接
            key += salt;
        }

        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
    }
}
