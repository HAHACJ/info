package com.info.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * user
 */

@Getter
@Setter
@ToString
public class User {

    private String username;

    private String userId;

    private String password;

    private String userType;

    /**
     * 接口认证参数
     */
    private String appKey;

    private Org org;

}
