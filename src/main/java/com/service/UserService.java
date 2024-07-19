package com.service;

import com.error.BusinessException;
import com.service.model.UserModel;

public interface UserService {

    UserModel getUserById(Integer id);
    void register(UserModel userModel) throws BusinessException;
}
