package com.controller;

import com.controller.viewobject.UserVO;
import com.error.BusinessException;
import com.error.CommonError;
import com.error.EmBusinessError;
import com.response.CommonReturnType;
import com.service.UserService;
import com.service.model.UserModel;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


@Controller("user")
@RequestMapping("/user")
@CrossOrigin(origins = "http:localhost:8080", allowCredentials = "true", allowedHeaders = "*")
public class UserController extends BaseController{

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    /**
     * user register
     * */
    @RequestMapping(value="/register", method= {RequestMethod.POST}, consumes={CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType register(@RequestParam(name="telephone")String telephone,
                                     @RequestParam(name="otpCode")String otpCode,
                                     @RequestParam(name="name")String name,
                                     @RequestParam(name="gender")Integer gender,
                                     @RequestParam(name="age")Integer age,
                                     @RequestParam(name="password")String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
//        verify whether telephone number matched the otp code
        String sessionOtpCode = (String)this.httpServletRequest.getSession().getAttribute(telephone);
        if(!com.alibaba.druid.util.StringUtils.equals(otpCode,sessionOtpCode)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"otp code does not match");
        }
//        user register
        UserModel userModel = new UserModel();
        userModel.setName(name);
        userModel.setGender(gender);
        userModel.setAge(age);
        userModel.setTelephone(telephone);
        userModel.setRegisterMode("byphone");
        userModel.setEncryptPassword(this.EncodeByMD5(password));

        userService.register(userModel);
        return CommonReturnType.create(null);

    }

    public String EncodeByMD5(String str) throws UnsupportedEncodingException, NoSuchAlgorithmException {
//        use base64 to encrypt
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        Base64.Encoder base64 = Base64.getEncoder();

        String newstr = base64.encodeToString(md5.digest(str.getBytes("utf-8")));
        return newstr;

    }


    /**
     * user receive one time password
     * */
    @RequestMapping(value="/getotp", method= {RequestMethod.POST}, consumes={CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType getOtp(@RequestParam(name="telephone")String telephone){
//        follow rules to generate otp verification code
        Random random = new Random();
        int randomInt = random.nextInt(99999);
        randomInt += 10000;
        String otpCode = String.valueOf(randomInt);
//        connect otp verification code to user's telephone number
//        use httpsession to bind them
        httpServletRequest.getSession().setAttribute(telephone, otpCode);

//        send otp verification code to user by message(not this time
        System.out.println("telephone="+telephone+"&otpCode"+otpCode); //
        return CommonReturnType.create(null);
    }



    /**
     *call service, get request user by id, and return to front end
     * */
    @RequestMapping("/get")
    @ResponseBody
    public CommonReturnType getUser(@RequestParam(name="id") Integer id) throws BusinessException {
        UserModel userById = userService.getUserById(id);

//        if user info does not exist
        if(userById == null){
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }

        UserVO userVO = convertFromModel(userById);
        return CommonReturnType.create(userVO);
    }

    private UserVO convertFromModel(UserModel userModel){
        if(userModel == null){
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userModel,userVO);
        return userVO;
    }



}
