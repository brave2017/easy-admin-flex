package com.easy.modules.sys.model.qo;


import com.easy.common.page.PageEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Maw
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DictTypeQo extends PageEntity {

    @ApiModelProperty(value = "字典类型")
    private String dictType;
    @ApiModelProperty(value = "字典名称")
    private String dictName;
}
