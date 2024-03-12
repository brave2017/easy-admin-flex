package com.easy.modules.sys.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.Data;

import java.util.Date;

/**
 * 角色
 *
 * @author Maw
 */
@Data
@Table(value = "sys_role")
public class SysRoleEntity {

	/**
	 * id
	 */
	@Id(keyType = KeyType.Generator,value = KeyGenerators.snowFlakeId)
	private Long id;

	/**
	 * 角色名称
	 */
	@Column(value = "name")
	private String name;

	/**
	 * 备注
	 */
	@Column(value = "remark")
	private String remark;

	/**
	 * 部门ID
	 */
	@Column(value = "dept_id")
	private Long deptId;

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
	@Column(value = "is_delete",isLogicDelete = true)
	private Integer isDelete;
}
