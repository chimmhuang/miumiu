package com.miumiu.user.dto;

import lombok.Data;

/**
 * @author chimmhuang
 * @date 2019/1/9 0009 17:35
 * @description 登录凭证
 */
@Data
public class LoginCertificateDTO {

    private String openid; //用户唯一标识
    private String session_key; //会话密钥
    private int expires_in; //过期时间
    private String unionid; //用户在开放平台的唯一标识符
    private int errcode; //错误码
    private String errmsg; //错误信息
}
