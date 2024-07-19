package com.dao;

import com.dataobject.UserPasswordDO;

public interface UserPasswordDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_userpassword
     *
     * @mbg.generated Fri Jul 19 22:29:12 IST 2024
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_userpassword
     *
     * @mbg.generated Fri Jul 19 22:29:12 IST 2024
     */
    int insert(UserPasswordDO row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_userpassword
     *
     * @mbg.generated Fri Jul 19 22:29:12 IST 2024
     */
    int insertSelective(UserPasswordDO row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_userpassword
     *
     * @mbg.generated Fri Jul 19 22:29:12 IST 2024
     */
    UserPasswordDO selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_userpassword
     *
     * @mbg.generated Fri Jul 19 22:29:12 IST 2024
     */
    int updateByPrimaryKeySelective(UserPasswordDO row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_userpassword
     *
     * @mbg.generated Fri Jul 19 22:29:12 IST 2024
     */
    int updateByPrimaryKey(UserPasswordDO row);

    UserPasswordDO selectByUserId(Integer id);
}