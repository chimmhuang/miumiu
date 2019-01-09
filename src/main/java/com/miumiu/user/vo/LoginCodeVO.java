package com.miumiu.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author chimmhuang
 * @date 2019/1/9 0009 10:05
 * @description 小程序登录code接受
 */
@Data
@ApiModel("小程序登录code接受VO类")
public class LoginCodeVO {

    @ApiModelProperty(name = "code",value = "临时TOKEN",example = "KWHASBNJKGIAnkg")
    private String code;
}
