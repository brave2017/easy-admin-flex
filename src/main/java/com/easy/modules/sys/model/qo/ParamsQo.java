package com.easy.modules.sys.model.qo;


import com.easy.common.page.PageEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Maw
 */
@ApiModel(value = "参数管理")
@EqualsAndHashCode(callSuper = true)
@Data
public class ParamsQo extends PageEntity {

    @ApiModelProperty(value = "参数编码")
    private String paramCode;

}
