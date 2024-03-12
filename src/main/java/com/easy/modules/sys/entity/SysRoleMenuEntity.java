package com.easy.modules.sys.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.Data;

import java.util.Date;

/**
 * 角色菜单关系
 *
 * @author Maw
 * @since 1.0.0
 */
@Data
@Table(value = "sys_role_menu")
public class SysRoleMenuEntity {

	/**
	 * id
	 */
	@Id(keyType = KeyType.Generator,value = KeyGenerators.snowFlakeId)
	private Long id;

	/**
	 * 角色ID
	 */
	@Column(value = "role_id")
	private Long roleId;
	/**
	 * 菜单ID
	 */
	@Column(value = "menu_id")
	private Long menuId;

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

}
