package com.easy.modules.log.model.qo;


import com.easy.common.page.PageEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Maw
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LogOperationQo extends PageEntity {

    @ApiModelProperty(value = "状态 0失败 1成功")
    private Integer status;
}
