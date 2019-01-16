package com.miumiu.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author chimmhuang
 * @date 2019/1/14 0014 14:32
 * @description 小程序自动登录TOKEN接收类
 */
@Data
@ApiModel("小程序登录TOKEN接受VO类")
public class AutoLoginVO {

    @ApiModelProperty(name = "MIUMIUTOKEN",value = "用户TOKEN",example = "KWHASBNJKGIAnkg")
    private String MIUMIUTOKEN;
}
