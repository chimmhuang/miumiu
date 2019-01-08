package com.miumiu.base.domain.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Created by chimmhuang on 2018/12/15 0015.
 */
@Data
@ToString
@NoArgsConstructor
@ApiModel(value = "通用的返回结果")
public class ResponseResult implements Response {

    //操作是否成功
    @ApiModelProperty(name = "success",value = "操作是否成功",example = "true")
    boolean success = SUCCESS;

    //操作代码
    @ApiModelProperty(name = "code",value = "操作代码",example = "10000")
    int code = SUCCESS_CODE;

    //提示信息
    @ApiModelProperty(name = "message",value = "提示信息",example = "操作成功！")
    String message;

    public ResponseResult(ResultCode resultCode){
        this.success = resultCode.success();
        this.code = resultCode.code();
        this.message = resultCode.message();
    }

    public static ResponseResult SUCCESS() {
        return new ResponseResult(CommonCode.SUCCESS);
    }
    public static ResponseResult FAIL() {
        return new ResponseResult(CommonCode.FAIL);
    }
}
