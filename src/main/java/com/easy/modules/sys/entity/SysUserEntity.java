package com.easy.modules.sys.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.Data;

import java.util.Date;

/**
 * 系统用户
 *
 * @author Maw
 */
@Data
@Table(value = "sys_user")
public class SysUserEntity {

	/**
	 * id
	 */
	@Id(keyType = KeyType.Generator,value = KeyGenerators.snowFlakeId)
	private Long id;

	/**
	 * 用户名
	 */
	@Column(value = "username")
	private String username;

	/**
	 * 密码
	 */
	@Column(value = "password")
	private String password;

	/**
	 * 姓名
	 */
	@Column(value = "real_name")
	private String realName;

	/**
	 * 头像
	 */
	@Column(value = "head_url")
	private String headUrl;

	/**
	 * 性别 0男 1女 2保密
	 */
	@Column(value = "gender")
	private Integer gender;

	/**
	 * 邮箱
	 */
	@Column(value = "email")
	private String email;

	/**
	 * 手机号
	 */
	@Column(value = "mobile")
	private String mobile;

	/**
	 * 部门ID
	 */
	@Column(value = "dept_id")
	private Long deptId;

	/**
	 * 超级管理员 0否 1是
	 */
	@Column(value = "super_admin")
	private Integer superAdmin;

	/**
	 * 状态 0停用 1正常
	 */
	@Column(value = "status")
	private Integer status;

	/**
	 * 部门名称
	 */
	@Column(ignore = true)
	private String deptName;

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
	@Column(value = "create_date",onInsertValue = "now()")
	private Date createDate;

	/**
	 * 是否删除 0否 1是
	 */
	@Column(value = "is_delete")
	private Integer isDelete;

}
