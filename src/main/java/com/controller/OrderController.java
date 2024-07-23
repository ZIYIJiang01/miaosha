package com.controller;

import com.alibaba.druid.util.StringUtils;
import com.error.BusinessException;
import com.error.EmBusinessError;
import com.response.CommonReturnType;
import com.service.OrderService;
import com.service.model.OrderModel;
import com.service.model.UserModel;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Controller("order")
@RequestMapping("/order")
@CrossOrigin(origins = "http:localhost:8080", allowCredentials = "true", allowedHeaders = "*")
public class OrderController extends BaseController{

    @Autowired
    private OrderService orderService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private RedisTemplate redisTemplate;

//    create Order
    @RequestMapping(value="/createorder", method= {RequestMethod.POST}, consumes={CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType createOrder(@RequestParam(name="itemId")Integer itemId,
                                        @RequestParam(name="amount")Integer amount,
                                        @RequestParam(name="promoId", required=false)Integer promoId) throws BusinessException {

//        get user login information
//        Object isLogin = this.httpServletRequest.getSession().getAttribute("IS_LOGIN");
//        if(isLogin == null || !(Boolean)isLogin ){
//            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN,"user does not log in");
//        }
//        UserModel userModel = (UserModel) this.httpServletRequest.getSession().getAttribute("LOGIN_USER");

//        use redis and token
        String[] tokens = httpServletRequest.getParameterMap().get("token");
        if(tokens == null){
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN,"user does not log in");
        }
        UserModel userModel = (UserModel) redisTemplate.opsForValue().get(tokens);
        if(userModel == null){
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN,"user does not log in");
        }

        OrderModel orderModel = orderService.createOrder(userModel.getId(),itemId,promoId,amount);
    return CommonReturnType.create(null);

    }
}
