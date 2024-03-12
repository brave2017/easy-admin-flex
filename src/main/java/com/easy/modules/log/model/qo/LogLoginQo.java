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
public class LogLoginQo extends PageEntity {

    @ApiModelProperty(value = "状态 0失败 1成功 2账号已锁定")
    private Integer status;

    @ApiModelProperty(value = "用户名")
    private String creatorName;


}
