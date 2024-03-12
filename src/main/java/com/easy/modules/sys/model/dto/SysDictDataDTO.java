package com.easy.modules.sys.model.dto;

import com.easy.common.validator.group.AddGroup;
import com.easy.common.validator.group.DefaultGroup;
import com.easy.common.validator.group.UpdateGroup;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;

/**
 * 字典数据
 *
 * @author Maw
 */
@Data
@ApiModel(value = "字典数据")
public class SysDictDataDTO {

	@ApiModelProperty(value = "id")
	@Null(message="{id.null}", groups = AddGroup.class)
	@NotNull(message="{id.require}", groups = UpdateGroup.class)
	private Long id;

	@ApiModelProperty(value = "字典类型ID")
	@NotNull(message="{sysdict.type.require}", groups = DefaultGroup.class)
	private Long dictTypeId;

	@ApiModelProperty(value = "字典标签")
	@NotBlank(message="{sysdict.label.require}", groups = DefaultGroup.class)
	private String dictLabel;

	@ApiModelProperty(value = "字典值")
	private String dictValue;

	@ApiModelProperty(value = "备注")
	private String remark;

	@ApiModelProperty(value = "排序")
	@Min(value = 0, message = "{sort.number}", groups = DefaultGroup.class)
	private Integer sort;

	@ApiModelProperty(value = "创建时间")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Date createDate;

	@ApiModelProperty(value = "更新时间")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Date updateDate;
}
