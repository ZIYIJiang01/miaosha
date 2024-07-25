package com.controller;

import com.alibaba.druid.util.StringUtils;
import com.error.BusinessException;
import com.error.EmBusinessError;
import com.mq.MqProducer;
import com.response.CommonReturnType;
import com.service.ItemService;
import com.service.OrderService;
import com.service.PromoService;
import com.service.UserService;
import com.service.model.OrderModel;
import com.service.model.UserModel;
import com.util.CodeUtil;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.*;
import com.google.common.util.concurrent.RateLimiter;


import static java.lang.System.out;

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

    @Autowired
    private MqProducer mqProducer;

    @Autowired
    private ItemService itemService;

    @Autowired
    private PromoService promoService;

    private ExecutorService executorService;

    private RateLimiter orderCreateRateLimiter;

    @PostConstruct
    public void init(){
        executorService = Executors.newFixedThreadPool(20);
        orderCreateRateLimiter = RateLimiter.create(300);
    }


//    generate code
    @RequestMapping(value="/generateverifycode", method= {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public void generateVerifyCode(HttpServletResponse response) throws BusinessException, IOException {
        String token = httpServletRequest.getParameterMap().get("token")[0];
        if(token == null){
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN,"user does not log in");
        }
        UserModel userModel = (UserModel) redisTemplate.opsForValue().get(token);
        if(userModel == null){
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN,"user does not log in");
        }
        Map<String, Object> map = CodeUtil.generateCodeAndPic();
        redisTemplate.opsForValue().set("verify_code_"+userModel.getId(),map.get("code"));
        redisTemplate.expire("verify_code_"+userModel.getId(),5, TimeUnit.MINUTES);
        ImageIO.write((RenderedImage) map.get("codePic"), "jpeg", response.getOutputStream());


    }

    @RequestMapping(value="/generatetoken", method= {RequestMethod.POST}, consumes={CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType generateToken(@RequestParam(name="itemId")Integer itemId,
                                        @RequestParam(name="promoId")Integer promoId,
                                          @RequestParam(name="verifyCode")String verifyCode) throws BusinessException {
//        base on token get information
        String token = httpServletRequest.getParameterMap().get("token")[0];
        if(token == null){
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN,"user does not log in");
        }
        UserModel userModel = (UserModel) redisTemplate.opsForValue().get(token);
        if(userModel == null){
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN,"user does not log in");
        }

//        verify code
        String redisVerifyCode = (String) redisTemplate.opsForValue().get("verify_code_"+userModel.getId());
        if(StringUtils.isEmpty(redisVerifyCode)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"request invalid");
        }
        if(!redisVerifyCode.equals(verifyCode)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"request invalid");
        }

//        access second kill token
        String promoToken = promoService.generateSecondKillToken(promoId,itemId, userModel.getId());
        if(promoToken == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"generate token error");
        }
        return CommonReturnType.create(promoToken);
    }

//    create Order
    @RequestMapping(value="/createorder", method= {RequestMethod.POST}, consumes={CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType createOrder(@RequestParam(name="itemId")Integer itemId,
                                        @RequestParam(name="amount")Integer amount,
                                        @RequestParam(name="promoId", required=false)Integer promoId,
                                        @RequestParam(name="promoToken", required=false)String promoToken) throws BusinessException {

//        get user login information
//        Object isLogin = this.httpServletRequest.getSession().getAttribute("IS_LOGIN");
//        if(isLogin == null || !(Boolean)isLogin ){
//            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN,"user does not log in");
//        }
//        UserModel userModel = (UserModel) this.httpServletRequest.getSession().getAttribute("LOGIN_USER");

//        verify promo token is valid

//        ratelimit check
        if(!orderCreateRateLimiter.tryAcquire()){
            throw new BusinessException(EmBusinessError.RATE_LIMIT);
        }

//        use redis and token
        String token = httpServletRequest.getParameterMap().get("token")[0];
        if(token == null){
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN,"user does not log in");
        }
        UserModel userModel = (UserModel) redisTemplate.opsForValue().get(token);
        if(userModel == null){
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN,"user does not log in");
        }

        if(promoToken != null){
            String inRedisPromoToken = (String)redisTemplate.opsForValue().get("promo_token_"+promoId+"_userid_"+userModel.getId()+"_itemid_"+itemId);
            if(inRedisPromoToken == null){
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"promo token invalid");
            }
            if(!StringUtils.equals(promoToken, inRedisPromoToken)){
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"promo token invalid");
            }
        }

//        use threads pool submit method
        Future<Object> future = executorService.submit(new Callable<Object>() {

            @Override
            public Object call() throws Exception {
//        OrderModel orderModel = orderService.createOrder(userModel.getId(),itemId,promoId,amount);
//        add stock log init status
                String stockLogId = itemService.initStockLog(itemId,amount);

//        then send transactional message
                if(!mqProducer.transactionAsyncReduceStock(userModel.getId(),itemId,promoId,amount,stockLogId)){
                    throw new BusinessException(EmBusinessError.UNKNOWN_ERROR, "order fail");
                }return null;
            }
        });

        try {
            future.get();
        } catch (InterruptedException e) {
            throw new BusinessException(EmBusinessError.UNKNOWN_ERROR);
        } catch (ExecutionException e) {
            throw new BusinessException(EmBusinessError.UNKNOWN_ERROR);
        }

        return CommonReturnType.create(null);

    }
}
