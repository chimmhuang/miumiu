package com.miumiu.domain.user.response;

import com.miumiu.base.domain.response.ResponseResult;
import com.miumiu.base.domain.response.ResultCode;
import com.miumiu.domain.user.entity.User;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Created by chimmhuang on 2018/12/15 0015.
 */
@Data
@NoArgsConstructor
@ApiModel(value = "用户模块的返回结果")
public class UserResult extends ResponseResult {
    User user;
    public UserResult(ResultCode resultCode, User user) {
        super(resultCode);
        this.user = user;
    }
}
