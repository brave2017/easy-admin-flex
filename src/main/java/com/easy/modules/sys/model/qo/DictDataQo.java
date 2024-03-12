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
public class DictDataQo extends PageEntity {

    @ApiModelProperty(value = "字典标签")
    private String dictLabel;
    @ApiModelProperty(value = "字典值")
    private String dictValue;

    @ApiModelProperty(value = "类型id")
    private Long dictTypeId;

}
