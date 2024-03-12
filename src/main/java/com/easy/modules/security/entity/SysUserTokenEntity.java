package com.easy.modules.security.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.Data;

import java.util.Date;

/**
 * 系统用户Token
 */
@Data
@Table(value = "sys_user_token")
public class SysUserTokenEntity {
	/**
	 * id
	 */
	@Id(keyType = KeyType.Generator,value = KeyGenerators.snowFlakeId)
	private Long id;
	/**
	 * 用户ID
	 */
	@Column(value = "user_id")
	private Long userId;
	/**
	 * 用户token
	 */
	@Column(value = "token")
	private String token;
	/**
	 * 过期时间
	 */
	@Column(value = "expire_date")
	private Date expireDate;
	/**
	 * 更新时间
	 */
	@Column(value = "update_date")
	private Date updateDate;
	/**
	 * 创建时间
	 */
	@Column(value = "create_date")
	private Date createDate;

}
