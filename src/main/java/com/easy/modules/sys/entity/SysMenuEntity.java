package com.easy.modules.sys.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.Data;

import java.util.Date;

/**
 * 菜单管理
 *
 * @author Maw
 */
@Data
@Table(value = "sys_menu")
public class SysMenuEntity {

	/**
	 * id
	 */
	@Id(keyType = KeyType.Generator,value = KeyGenerators.snowFlakeId)
	private Long id;

	/**
	 * 父菜单ID，一级菜单为0
	 */
	@Column(value = "pid")
	private Long pid;

	/**
	 * 菜单名称
	 */
	@Column(value = "name")
	private String name;

	/**
	 * 菜单URL
	 */
	@Column(value = "url")
	private String url;

	/**
	 * 授权(多个用逗号分隔，如：sys:user:list,sys:user:save)
	 */
	@Column(value = "permissions")
	private String permissions;

	/**
	 * 类型 0菜单 1按钮
	 */
	@Column(value = "menu_type")
	private Integer menuType;

	/**
	 * 菜单图标
	 */
	@Column(value = "icon")
	private String icon;

	/**
	 * 排序
	 */
	@Column(value = "sort")
	private Integer sort;

	/**
	 * 上级菜单名称
	 */
	@Column(ignore = true)
	private String parentName;

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
