package com.dao;

import com.dataobject.SequenceDO;

public interface SequenceDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sequenceinfo
     *
     * @mbg.generated Sun Jul 21 10:39:09 IST 2024
     */
    int deleteByPrimaryKey(String name);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sequenceinfo
     *
     * @mbg.generated Sun Jul 21 10:39:09 IST 2024
     */
    int insert(SequenceDO row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sequenceinfo
     *
     * @mbg.generated Sun Jul 21 10:39:09 IST 2024
     */
    int insertSelective(SequenceDO row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sequenceinfo
     *
     * @mbg.generated Sun Jul 21 10:39:09 IST 2024
     */
    SequenceDO getSequenceByName(String name);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sequenceinfo
     *
     * @mbg.generated Sun Jul 21 10:39:09 IST 2024
     */
    int updateByPrimaryKeySelective(SequenceDO row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sequenceinfo
     *
     * @mbg.generated Sun Jul 21 10:39:09 IST 2024
     */
    int updateByPrimaryKey(SequenceDO row);
}