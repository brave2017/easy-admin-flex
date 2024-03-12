package com.easy.modules.security.service;

import com.easy.common.utils.Result;
import com.easy.modules.security.entity.SysUserTokenEntity;
import com.mybatisflex.core.service.IService;

import java.util.Map;

/**
 * 用户Token
 *
 * @author Maw
 */
public interface SysUserTokenService extends IService<SysUserTokenEntity> {

	/**
	 * 生成token
	 * @param userId  用户ID
	 */
	Result<Map<String, Object>> createToken(Long userId);

	/**
	 * 退出，修改token值
	 * @param userId  用户ID
	 */
	void logout(Long userId);

}
