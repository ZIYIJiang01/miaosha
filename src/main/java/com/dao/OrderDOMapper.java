package com.dao;

import com.dataobject.OrderDO;

public interface OrderDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_orderinfo
     *
     * @mbg.generated Sun Jul 21 09:49:49 IST 2024
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_orderinfo
     *
     * @mbg.generated Sun Jul 21 09:49:49 IST 2024
     */
    int insert(OrderDO row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_orderinfo
     *
     * @mbg.generated Sun Jul 21 09:49:49 IST 2024
     */
    int insertSelective(OrderDO row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_orderinfo
     *
     * @mbg.generated Sun Jul 21 09:49:49 IST 2024
     */
    OrderDO selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_orderinfo
     *
     * @mbg.generated Sun Jul 21 09:49:49 IST 2024
     */
    int updateByPrimaryKeySelective(OrderDO row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_orderinfo
     *
     * @mbg.generated Sun Jul 21 09:49:49 IST 2024
     */
    int updateByPrimaryKey(OrderDO row);
}