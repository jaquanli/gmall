package com.atguigu.gmall.cart.controller;

import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.bean.CartInfo;
import com.atguigu.gmall.bean.SkuInfo;
import com.atguigu.gmall.consts.WebConst;
import com.atguigu.gmall.utils.CartUtil;
import com.atguigu.gmall.utils.CookieUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class CartCookieHandler {


    /**
     * 去本地购物添加或更新Cookie
     *
     * @param skuInfo  数据库得到的对象
     * @param skuNum   页面传来的商品数量
     * @param request  request对象
     * @param response response对象
     */
    void addToCartFromCookie(SkuInfo skuInfo, String skuNum, HttpServletRequest request, HttpServletResponse response) {

        //获取数据
        Long skuId = skuInfo.getId();
        BigDecimal skuPrice = skuInfo.getPrice();
        String skuDefaultImg = skuInfo.getSkuDefaultImg();
        String skuName = skuInfo.getSkuName();
        //为封装对象为了添加
        CartInfo cartInfoAdd = new CartInfo(null, null, skuId, skuPrice, null, skuDefaultImg, "1", skuPrice, Integer.parseInt(skuNum), skuName);
        //为了方便简化代码
        String cartInfoListJson;
        //从cookie中获取cartInfoList
        List<CartInfo> cartInfoList = getCartInfoListByCookie(request);
        //如果不为空就更新
        if (cartInfoList != null && cartInfoList.size() > 0) {
            //调用工具类中的判断是否新的购物车
            boolean resultFlag = CartUtil.ifNewCart(cartInfoList,cartInfoAdd);
            if (resultFlag){
                cartInfoList.add(cartInfoAdd);
            }else {
                //遍历cartInfos
                for (CartInfo cartInfo : cartInfoList) {

                    //当页面的购物车中商品id与数据库查出的商品id一致的情况，就进行更新数量和价格
                    if (cartInfo.getSkuId().equals(skuId)) {
                        //更新数量，等于原来的数量加上用户添加的数量
                        cartInfo.setSkuNum(cartInfo.getSkuNum() + Integer.parseInt(skuNum));
                        //更新购物车的价格，等于数据库中商品的单价乘以更新后的数量
                        cartInfo.setCartPrice(skuPrice.multiply(new BigDecimal(cartInfo.getSkuNum())));
                        //在查到一致的情况下将
                        //cartInfoList.add(cartInfo);
                    }
                }
            }
            //循环更新完之后再次放入json进行更新
            cartInfoListJson = JSON.toJSONString(cartInfoList);
        } else {
            //添加到list
            cartInfoList.add(cartInfoAdd);
            cartInfoListJson = JSON.toJSONString(cartInfoList);
        }
        //设置Cookie
        CookieUtil.setCookie(request, response, WebConst.CART_COOKIE_NAME, cartInfoListJson, WebConst.COOKIE_OVER_TIME_MAX, true);

    }

    /**
     * 获取cookie中的数据
     *
     * @return 返回cookie中的CarInfoList数据
     */
    private List<CartInfo> getCartInfoListByCookie(HttpServletRequest request) {

        //获取购物车中的Cookie
        String cartListJson = CookieUtil.getCookieValue(request, WebConst.CART_COOKIE_NAME, true);
        if (StringUtils.isNotBlank(cartListJson)) {
            //通过fastJson方法获取cookie中的cartInfoList
            return JSON.parseArray(cartListJson, CartInfo.class);
        }
        return new ArrayList<>();
    }

    /**
     * 根据选中状态和sku对应的id进行更新cookie中数据
     * @param skuId 商品id
     * @param isChecked 选择状态
     * @return List<CartInfo>
     */
    List<CartInfo> updateToCartFromCookie(String skuId, String isChecked, HttpServletRequest request,HttpServletResponse response) {
        //通过cookie的key获取到cookie中所有的CartInfo对象
        List<CartInfo> cartInfoListByCookie = getCartInfoListByCookie(request);
        //遍历，当要改变选中状态的id与cookie中的skuId一致时，改变为当前的选择状态
        for (CartInfo cartInfo : cartInfoListByCookie) {
            if (cartInfo.getSkuId().equals(Long.parseLong(skuId))){
                //更新选中状态
                cartInfo.setIsChecked(isChecked);
            }
        }
        //将list转json对象
        String cartInfoListJson = JSON.toJSONString(cartInfoListByCookie);
        //更新cookie缓存
        CookieUtil.setCookie(request, response, WebConst.CART_COOKIE_NAME, cartInfoListJson, WebConst.COOKIE_OVER_TIME_MAX, true);
        //将改变后的值返回
        return cartInfoListByCookie;

    }
}
