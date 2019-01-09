package com.miumiu.user.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author chimmhuang
 * @date 2019/1/9 0009 10:30
 * @description 用户敏感数据
 */
@Data
public class DecryptVO {

    @ApiModelProperty(name = "encryptedData",value = "包括敏感数据在内的完整用户信息的加密数据")
    private String encryptedData;

    @ApiModelProperty(name = "sessionKey",value = "数据进行加密签名的密钥")
    private String sessionKey;

    @ApiModelProperty(name = "iv",value = "加密算法的初始向量")
    private String iv;
}
