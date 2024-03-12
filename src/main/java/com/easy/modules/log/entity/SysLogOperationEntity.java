package com.easy.modules.log.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.Data;

import java.util.Date;

/**
 * 操作日志
 *
 * @author Maw
 * @since 1.0.0
 */
@Data
@Table(value = "sys_log_operation")
public class SysLogOperationEntity {
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
	 * 用户操作
	 */
	@Column(value = "operation")
	private String operation;
	/**
	 * 请求URI
	 */
	@Column(value = "request_uri")
	private String requestUri;
	/**
	 * 请求方式
	 */
	@Column(value = "request_method")
	private String requestMethod;
	/**
	 * 请求参数
	 */
	@Column(value = "request_params")
	private String requestParams;
	/**
	 * 请求时长(毫秒)
	 */
	@Column(value = "request_time")
	private Integer requestTime;
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
	 * 状态  0：失败   1：成功
	 */
	@Column(value = "status")
	private Integer status;

	/**
	 * 用户名
	 */
	@Column(value = "creator_name")
	private String creatorName;
}
