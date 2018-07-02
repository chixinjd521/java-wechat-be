package com.dadiyang.wx.entity;

import java.util.Date;

public class SystemUser extends SystemUserKey {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column system_user.username
     *
     * @mbg.generated Thu Jun 28 14:13:28 CST 2018
     */
    private String username;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column system_user.password
     *
     * @mbg.generated Thu Jun 28 14:13:28 CST 2018
     */
    private String password;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column system_user.real_name
     *
     * @mbg.generated Thu Jun 28 14:13:28 CST 2018
     */
    private String realName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column system_user.phone
     *
     * @mbg.generated Thu Jun 28 14:13:28 CST 2018
     */
    private String phone;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column system_user.email
     *
     * @mbg.generated Thu Jun 28 14:13:28 CST 2018
     */
    private String email;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column system_user.create_time
     *
     * @mbg.generated Thu Jun 28 14:13:28 CST 2018
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column system_user.update_time
     *
     * @mbg.generated Thu Jun 28 14:13:28 CST 2018
     */
    private Date updateTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column system_user.is_disabled
     *
     * @mbg.generated Thu Jun 28 14:13:28 CST 2018
     */
    private Boolean isDisabled;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column system_user.username
     *
     * @return the value of system_user.username
     *
     * @mbg.generated Thu Jun 28 14:13:28 CST 2018
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column system_user.username
     *
     * @param username the value for system_user.username
     *
     * @mbg.generated Thu Jun 28 14:13:28 CST 2018
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column system_user.password
     *
     * @return the value of system_user.password
     *
     * @mbg.generated Thu Jun 28 14:13:28 CST 2018
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column system_user.password
     *
     * @param password the value for system_user.password
     *
     * @mbg.generated Thu Jun 28 14:13:28 CST 2018
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column system_user.real_name
     *
     * @return the value of system_user.real_name
     *
     * @mbg.generated Thu Jun 28 14:13:28 CST 2018
     */
    public String getRealName() {
        return realName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column system_user.real_name
     *
     * @param realName the value for system_user.real_name
     *
     * @mbg.generated Thu Jun 28 14:13:28 CST 2018
     */
    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column system_user.phone
     *
     * @return the value of system_user.phone
     *
     * @mbg.generated Thu Jun 28 14:13:28 CST 2018
     */
    public String getPhone() {
        return phone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column system_user.phone
     *
     * @param phone the value for system_user.phone
     *
     * @mbg.generated Thu Jun 28 14:13:28 CST 2018
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column system_user.email
     *
     * @return the value of system_user.email
     *
     * @mbg.generated Thu Jun 28 14:13:28 CST 2018
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column system_user.email
     *
     * @param email the value for system_user.email
     *
     * @mbg.generated Thu Jun 28 14:13:28 CST 2018
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column system_user.create_time
     *
     * @return the value of system_user.create_time
     *
     * @mbg.generated Thu Jun 28 14:13:28 CST 2018
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column system_user.create_time
     *
     * @param createTime the value for system_user.create_time
     *
     * @mbg.generated Thu Jun 28 14:13:28 CST 2018
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column system_user.update_time
     *
     * @return the value of system_user.update_time
     *
     * @mbg.generated Thu Jun 28 14:13:28 CST 2018
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column system_user.update_time
     *
     * @param updateTime the value for system_user.update_time
     *
     * @mbg.generated Thu Jun 28 14:13:28 CST 2018
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column system_user.is_disabled
     *
     * @return the value of system_user.is_disabled
     *
     * @mbg.generated Thu Jun 28 14:13:28 CST 2018
     */
    public Boolean getIsDisabled() {
        return isDisabled;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column system_user.is_disabled
     *
     * @param isDisabled the value for system_user.is_disabled
     *
     * @mbg.generated Thu Jun 28 14:13:28 CST 2018
     */
    public void setIsDisabled(Boolean isDisabled) {
        this.isDisabled = isDisabled;
    }
}