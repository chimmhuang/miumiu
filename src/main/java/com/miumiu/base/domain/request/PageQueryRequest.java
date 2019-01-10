package com.miumiu.base.domain.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huangshuai
 * @date 2019-01-10 13:16
 * @description 分页搜索
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("分页查询接收对象")
public class PageQueryRequest {

    @ApiModelProperty(name = "page",value = "当前页（起始1）",example = "1")
    private int page = 1;

    @ApiModelProperty(name = "size",value = "一页显示数",example = "10")
    private int size = 10;
}
