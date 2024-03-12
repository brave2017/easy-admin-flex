package com.easy.modules.sys.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.Data;

import java.util.Date;

/**
 * 字典类型
 *
 * @author Maw
 */
@Data
@Table(value = "sys_dict_type")
public class SysDictTypeEntity {
	/**
	 * id
	 */
	@Id(keyType = KeyType.Generator,value = KeyGenerators.snowFlakeId)
	private Long id;

	/**
	 * 字典类型
	 */
	@Column(value = "dict_type")
	private String dictType;

	/**
	 * 字典名称
	 */
	@Column(value = "dict_name")
	private String dictName;

	/**
	 * 备注
	 */
	@Column(value = "remark")
	private String remark;

	/**
	 * 排序
	 */
	@Column(value = "sort")
	private Integer sort;

	/**
	 * 更新者
	 */
	@Column(value = "updater")
	private Long updater;

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
