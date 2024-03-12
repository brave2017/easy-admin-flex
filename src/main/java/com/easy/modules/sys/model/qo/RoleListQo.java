package com.easy.modules.sys.model.qo;


import com.easy.common.page.PageEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 角色列表请求参数
 * @author Maw
 */
@ApiModel(value = "角色列表请求参数")
@EqualsAndHashCode(callSuper = true)
@Data
public class RoleListQo extends PageEntity {
    @ApiModelProperty(value = "角色名称")
    private String name;

}
