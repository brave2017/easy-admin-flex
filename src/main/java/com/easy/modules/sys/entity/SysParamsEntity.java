package com.easy.modules.sys.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.Data;

import java.util.Date;

/**
 * 参数管理
 *
 * @author Maw
 * @since 1.0.0
 */
@Data
@Table(value = "sys_params")
public class SysParamsEntity {

	/**
	 * id
	 */
	@Id(keyType = KeyType.Generator,value = KeyGenerators.snowFlakeId)
	private Long id;

	/**
	 * 参数编码
	 */
	@Column(value = "param_code")
	private String paramCode;

	/**
	 * 参数值
	 */
	@Column(value = "param_value")
	private String paramValue;

	/**
	 * 类型 0系统参数 1非系统参数
	 */
	@Column(value = "param_type")
	private Integer paramType;

	/**
	 * 备注
	 */
	@Column(value = "remark")
	private String remark;

	/**
	 * 更新者
	 */
	@Column(value = "updater")
	private Long updater;

	/**
	 * 更新者
	 */
	@Column(value = "updater_name")
	private String updaterName;

	/**
	 * 更新时间
	 */
	@Column(value = "update_date")
	private Date updateDate;

	/**
	 * 创建者
	 */
	@Column(value = "creator")
	private Long creator;

	/**
	 * 创建者
	 */
	@Column(value = "creator_name")
	private String creatorName;

	/**
	 * 创建时间
	 */
	@Column(value = "create_date")
	private Date createDate;

	/**
	 * 是否删除 0否 1是
	 */
	@Column(value = "is_delete")
	private Integer isDelete;

}
