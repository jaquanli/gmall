package com.atguigu.gmall.cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.annotations.LoginInterceptCondition;
import com.atguigu.gmall.bean.CartInfo;
import com.atguigu.gmall.bean.SkuInfo;
import com.atguigu.gmall.consts.WebConst;
import com.atguigu.gmall.service.CartService;
import com.atguigu.gmall.service.SkuService;
import com.atguigu.gmall.utils.CookieUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class CartController {

    @Reference
    private SkuService skuService;

    private final CartCookieHandler cartCookieHandler;

    @Reference
    private CartService cartService;

    @Autowired
    public CartController(CartCookieHandler cartCookieHandler) {
        this.cartCookieHandler = cartCookieHandler;
    }

    /***
     * 当用户选择单个商品时改变选中状态，
     * 通过判断用户是否登录，选择更新cookie的数据还是DB的数据
     * @param map 请求参数
     * @return 转发页面
     */
    @LoginInterceptCondition(isRedirect = false)
    @RequestMapping("checkCart")
    public String modifyCartCheck(@RequestParam Map<String,String> map,HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        String isChecked = map.get("isChecked");
        String skuId = map.get("skuId");
        //需要获取用户的信息
        //String userId = "1";
        String userId = (String) request.getAttribute("userId");
        List<CartInfo> cartInfoList;
        if (StringUtils.isNotBlank(userId)){
            //更新db
            cartInfoList = cartService.updateCartCheck(isChecked,skuId,userId);
        }else {
            //更新cookie
            cartInfoList = cartCookieHandler.updateToCartFromCookie(skuId,isChecked,request,response);
        }

        modelMap.put("cartList", cartInfoList);

        return "cartList";

    }

    /***
     * 用户选择商品添加到购物车，通过判断用户是否登录，选择存入的数据是cookie还是DB
     * @return 视图
     */
    @LoginInterceptCondition(isRedirect = false)
    @RequestMapping("addToCart")
    public ModelAndView addToCart(HttpServletRequest request, HttpServletResponse response) {
        //根据request获取请求中的参数
        String skuId = request.getParameter("skuId");
        String skuNum = request.getParameter("num");
        //获取是skuInfo对象
        SkuInfo skuInfo = skuService.querySkuInfoById(skuId);
        //获取默认图片
        String skuDefaultImg = skuInfo.getSkuDefaultImg();
        //需要获取用户的信息
        //String userId = "1";
        String userId = (String) request.getAttribute("userId");
        if (StringUtils.isNotBlank(userId)) {
            //在这说明用户已登录，操作DB
            cartService.addToCartFromDB(userId, skuInfo, skuNum);
        } else {
            //在这说明用户未登录，操作cookie，包括检查是否存在就更新还是新增
            cartCookieHandler.addToCartFromCookie(skuInfo, skuNum, request, response);
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/cartAddSuccess");
        //modelAndView.addObject("skuInfo",skuInfo);
        modelAndView.addObject("skuName",skuInfo.getSkuName());
        modelAndView.addObject("id",skuInfo.getId());
        modelAndView.addObject("skuDefaultImg",skuInfo.getSkuDefaultImg());
        modelAndView.addObject("skuNum",skuNum);
        RedirectAttributes redirectAttributes;
        /**
         * 这里直接使用redirect会默认使用iso8859-1编码方式，遇到中文会产生乱码
         * 需要使用modelAndView进行重定向，会自动识别浏览器响应字符集
         */
        return modelAndView;
    }

    /***
     * 通过判断用户是否登录，选择加载的数据是cookie还是DB
     * @return 转发视图
     */
    @LoginInterceptCondition(isRedirect = false)
    @RequestMapping("cartList")
    public String goCartListPage(HttpServletRequest request, ModelMap modelMap) {

        //需要获取用户的信息
        //String userId = "1";
        String userId = (String) request.getAttribute("userId");

        List<CartInfo> cartInfos = new ArrayList<>();
        if (StringUtils.isBlank(userId)) {
            //从cookie中获取值
            String cartCookieValue = CookieUtil.getCookieValue(request, WebConst.CART_COOKIE_NAME, true);
            if (StringUtils.isNotBlank(cartCookieValue)) {
                //有数据就转换成list
                cartInfos = JSON.parseArray(cartCookieValue, CartInfo.class);

            }
//            else {
//                //购物车无数据
//            }
        } else {
            //从DB中取值
            cartInfos = cartService.getCartListFromCache(userId);
        }
        if (cartInfos != null) {

            BigDecimal totalPrice = new BigDecimal(0);
            for (CartInfo cartInfo : cartInfos) {
                if ("1".equals(cartInfo.getIsChecked())){
                    BigDecimal cartPrice = cartInfo.getCartPrice();
                    totalPrice = totalPrice.add(cartPrice);
                }

            }
            modelMap.put("totalPrice",totalPrice);
        }
        modelMap.put("cartList", cartInfos);


        return "cartList";

    }

    /***
     * 转发到添加成功的页面
     * @param skuInfo 携带的SkuInfo信息
     * @param skuNum 携带的SkuNum信息
     * @return 转发
     */
    @LoginInterceptCondition(isRedirect = false)
    @RequestMapping("cartAddSuccess")
    public String goCartAddSuccess(SkuInfo skuInfo,String skuNum, ModelMap modelMap) {

        modelMap.put("skuInfo", skuInfo);
        modelMap.put("skuNum", skuNum);

        return "success";
    }

}
