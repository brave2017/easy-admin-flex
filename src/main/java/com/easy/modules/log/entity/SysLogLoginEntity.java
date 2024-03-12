package com.easy.modules.log.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.Data;

import java.util.Date;

/**
 * 登录日志
 *
 * @author Maw
 * @since 1.0.0
 */
@Data
@Table(value = "sys_log_login")
public class SysLogLoginEntity {
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
	 * 用户操作   0：用户登录   1：用户退出
	 */
	@Column(value = "operation")
	private Integer operation;

	/**
	 * 状态  0：失败    1：成功    2：账号已锁定
	 */
	@Column(value = "status")
	private Integer status;

	/**
	 * 用户代理
	 */
	@Column(value = "user_agent")
	private String userAgent;

	/**
	 * 操作IP
	 */
	@Column(value = "ip")
	private String ip;

	/**
	 * 用户名
	 */
	@Column(value = "creator_name")
	private String creatorName;

}
