package cn.nianzx.domain;

import java.io.Serializable;

public class User implements Serializable {
    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 登录名
     */
    private String userCode;

    /**
     * 姓名
     */
    private String userName;

    /**
     * 密码
     */
    private String pwd;

    /**
     * 账号未过期
     */
    private Boolean isAccountNotExpired;

    /**
     * 账号未锁定
     */
    private Boolean isAccountNotLocked;

    /**
     * 密码未过期
     */
    private Boolean isCredentialsNonExpired;

    /**
     * 账号被启用
     */
    private Boolean isEnabled;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 性别 0女 1男
     */
    private Integer sex;

    private static final long serialVersionUID = 1L;

    /**
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取登录名
     *
     * @return user_code - 登录名
     */
    public String getUserCode() {
        return userCode;
    }

    /**
     * 设置登录名
     *
     * @param userCode 登录名
     */
    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    /**
     * 获取姓名
     *
     * @return user_name - 姓名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置姓名
     *
     * @param userName 姓名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取密码
     *
     * @return pwd - 密码
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * 设置密码
     *
     * @param pwd 密码
     */
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    /**
     * 获取账号未过期
     *
     * @return is_account_not_expired - 账号未过期
     */
    public Boolean getAccountNotExpired() {
        return isAccountNotExpired;
    }

    /**
     * 设置账号未过期
     *
     * @param accountNotExpired 账号未过期
     */
    public void setAccountNotExpired(Boolean accountNotExpired) {
        isAccountNotExpired = accountNotExpired;
    }

    /**
     * 获取账号未锁定
     *
     * @return is_account_not_locked - 账号未锁定
     */
    public Boolean getAccountNotLocked() {
        return isAccountNotLocked;
    }


    /**
     * 设置账号未锁定
     *
     * @param accountNotLocked 账号未锁定
     */
    public void setAccountNotLocked(Boolean accountNotLocked) {
        isAccountNotLocked = accountNotLocked;
    }


    /**
     * 获取密码未过期
     *
     * @return is_credentials_non_expired - 密码未过期
     */
    public Boolean getCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }


    /**
     * 设置密码未过期
     *
     * @param credentialsNonExpired 密码未过期
     */
    public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
        isCredentialsNonExpired = credentialsNonExpired;
    }


    /**
     * 获取账号被启用
     *
     * @return is_enabled - 账号被启用
     */
    public Boolean getEnabled() {
        return isEnabled;
    }


    /**
     * 设置账号被启用
     *
     * @param enabled 账号被启用
     */
    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    /**
     * 获取年龄
     *
     * @return age - 年龄
     */
    public Integer getAge() {
        return age;
    }

    /**
     * 设置年龄
     *
     * @param age 年龄
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * 获取性别 0女 1男
     *
     * @return sex - 性别 0女 1男
     */
    public String getSex() {
        return sex == 1 ? "男" : "女";
    }

    /**
     * 设置性别 0女 1男
     *
     * @param sex 性别 0女 1男
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }
}