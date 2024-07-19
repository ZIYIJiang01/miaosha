package com.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.dao.UserDOMapper;
import com.dao.UserPasswordDOMapper;
import com.dataobject.UserDO;
import com.dataobject.UserPasswordDO;
import com.error.BusinessException;
import com.error.EmBusinessError;
import com.service.UserService;
import com.service.model.UserModel;
import org.apache.catalina.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDOMapper userDOMapper;

    @Autowired
    private UserPasswordDOMapper userPasswordDOMapper;
    /**
     * get user model dataobject by calling userdo mapper and userpassworddo mapper,
     * */
    @Override
    public UserModel getUserById(Integer id){
        UserDO userDO = userDOMapper.selectByPrimaryKey(id);
        if(userDO == null){
            return null;
        }
        //get encrypt password by user id
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(id);

        return convertFromDataObject(userDO, userPasswordDO);
    }
    @Override
    @Transactional
    public void register(UserModel userModel) throws BusinessException {
        if(userModel == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        if(StringUtils.isEmpty(userModel.getName())
                ||userModel.getGender()==null||userModel.getAge()==null
                ||StringUtils.isEmpty(userModel.getTelephone())){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        UserDO userDO = convertFromModel(userModel);
        try{
            userDOMapper.insertSelective(userDO);//use insertSelective means if there's no data in parameter then use database default
        }catch(DuplicateKeyException ex){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"phone number has been registered");
        }
        userModel.setId(userDO.getId());

        UserPasswordDO userPasswordDO = convertPasswordFromModel(userModel);
        userPasswordDOMapper.insertSelective(userPasswordDO);

        return;
    }

//    convert to data object, because we need to transfer its information to database
    private UserDO convertFromModel(UserModel userModel){
        if (userModel == null) {
            return null;
        }
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userModel,userDO);
        return userDO;
    }
    private UserPasswordDO convertPasswordFromModel(UserModel userModel){
        if (userModel == null) {
            return null;
        }
        UserPasswordDO userPasswordDO = new UserPasswordDO();
        userPasswordDO.setEncryptPassword(userModel.getEncryptPassword());
        userPasswordDO.setUserId(userModel.getId());
        return userPasswordDO;
    }


//    convert to usermodel, to presents full information by business logic instead of only by table
    private UserModel convertFromDataObject(UserDO userDO, UserPasswordDO userPasswordDO){
        if(userDO==null){
            return null;
        }
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDO, userModel);
        if(userPasswordDO!=null) {
            userModel.setEncryptPassword(userPasswordDO.getEncryptPassword());
        }
        return userModel;
    }

}
