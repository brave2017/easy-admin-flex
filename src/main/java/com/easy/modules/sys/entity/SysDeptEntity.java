package com.easy.modules.sys.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.Data;

import java.util.Date;

/**
 * 部门管理
 *
 * @author Maw
 */
@Data
@Table(value = "sys_dept")
public class SysDeptEntity {

	/**
	 * id
	 */
	@Id(keyType = KeyType.Generator,value = KeyGenerators.snowFlakeId)
	private Long id;

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
	 * 上级ID
	 */
	@Column(value = "pid")
	private Long pid;

	/**
	 * 所有上级ID，用逗号分开
	 */
	@Column(value = "pids")
	private String pids;

	/**
	 * 部门名称
	 */
	@Column(value = "name")
	private String name;
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
	 * 上级部门名称
	 */
	@Column(ignore = true)
	private String parentName;

}
