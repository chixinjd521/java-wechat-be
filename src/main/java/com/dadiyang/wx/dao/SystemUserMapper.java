package com.dadiyang.wx.dao;

import com.dadiyang.wx.entity.SystemUser;
import com.dadiyang.wx.entity.SystemUserExample;
import com.dadiyang.wx.entity.SystemUserKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SystemUserMapper extends BaseMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_user
     *
     * @mbg.generated Thu Jun 28 14:13:28 CST 2018
     */
    long countByExample(SystemUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_user
     *
     * @mbg.generated Thu Jun 28 14:13:28 CST 2018
     */
    int deleteByExample(SystemUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_user
     *
     * @mbg.generated Thu Jun 28 14:13:28 CST 2018
     */
    int deleteByPrimaryKey(SystemUserKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_user
     *
     * @mbg.generated Thu Jun 28 14:13:28 CST 2018
     */
    int insert(SystemUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_user
     *
     * @mbg.generated Thu Jun 28 14:13:28 CST 2018
     */
    int insertSelective(SystemUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_user
     *
     * @mbg.generated Thu Jun 28 14:13:28 CST 2018
     */
    List<SystemUser> selectByExample(SystemUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_user
     *
     * @mbg.generated Thu Jun 28 14:13:28 CST 2018
     */
    SystemUser selectByPrimaryKey(SystemUserKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_user
     *
     * @mbg.generated Thu Jun 28 14:13:28 CST 2018
     */
    int updateByExampleSelective(@Param("record") SystemUser record, @Param("example") SystemUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_user
     *
     * @mbg.generated Thu Jun 28 14:13:28 CST 2018
     */
    int updateByExample(@Param("record") SystemUser record, @Param("example") SystemUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_user
     *
     * @mbg.generated Thu Jun 28 14:13:28 CST 2018
     */
    int updateByPrimaryKeySelective(SystemUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_user
     *
     * @mbg.generated Thu Jun 28 14:13:28 CST 2018
     */
    int updateByPrimaryKey(SystemUser record);
}