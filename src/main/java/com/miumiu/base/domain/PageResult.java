package com.miumiu.base.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author huangshuai
 * @date 2019-01-10 11:49
 * @description 分页结果集对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("分页结果集对象")
public class PageResult<T> implements Serializable {

    @ApiModelProperty(name = "totalElements",value = "总记录数",example = "20")
    private Long totalElements; //总记录数

    @ApiModelProperty(name = "page",value = "当前页",example = "1")
    private int page; //当前页

    @ApiModelProperty(name = "totalPage",value = "总页数",example = "50")
    private int totalPage; //总页数

    @ApiModelProperty(name = "rows",value = "结果集")
    private List<T> rows; //查询结果
}
