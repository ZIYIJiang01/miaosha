package com.service;

import com.error.BusinessException;
import com.service.model.UserModel;

public interface UserService {

    UserModel getUserById(Integer id);

//    get user by cache
    UserModel getUserByIdInCache(Integer id);

    void register(UserModel userModel) throws BusinessException;
    /**
     * telephone: telephone user registered
     * password: encrypted password
     * */
    UserModel validateLogin(String telephone, String encryptPassword) throws BusinessException;
}
