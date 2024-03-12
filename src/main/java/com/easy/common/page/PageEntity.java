package com.easy.common.page;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分页实体
 * @author Maw
 */
@ApiModel(value = "分页实体")
@Data
public class PageEntity {

    @ApiModelProperty("页数")
    private Integer page;

    @ApiModelProperty("每页数量")
    private Integer limit;

}
