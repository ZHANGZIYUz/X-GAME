package com.example.backend.model.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户注册请求体
 */
@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    private long id;

    private String username;

    private String userAccount;

    private String userPassword;

    private String avatarUrl;

    private String userProfile;

    private int gender;

    private int userRole;

    private String tags;

    private Date createTime;

}
