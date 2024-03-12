package com.easy.modules.security.service.impl;

import com.easy.common.constant.Constant;
import com.easy.common.utils.Result;
import com.easy.modules.security.dao.SysUserTokenDao;
import com.easy.modules.security.entity.SysUserTokenEntity;
import com.easy.modules.security.oauth2.TokenGenerator;
import com.easy.modules.security.service.SysUserTokenService;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class SysUserTokenServiceImpl extends ServiceImpl<SysUserTokenDao, SysUserTokenEntity> implements SysUserTokenService {
	/**
	 * 12小时后过期
	 */
	private final static int EXPIRE = 3600 * 12;

	@Override
	public Result<Map<String, Object>> createToken(Long userId) {
		//用户token
		String token;

		//当前时间
		Date now = new Date();
		//过期时间
		Date expireTime = new Date(now.getTime() + EXPIRE * 1000);

		//判断是否生成过token
		QueryWrapper wrapper = new QueryWrapper();
		wrapper.eq(SysUserTokenEntity::getUserId,userId);
		SysUserTokenEntity tokenEntity = mapper.selectOneByQuery(wrapper);

		if(tokenEntity == null){
			//生成一个token
			token = TokenGenerator.generateValue();

			tokenEntity = new SysUserTokenEntity();
			tokenEntity.setUserId(userId);
			tokenEntity.setToken(token);
			tokenEntity.setUpdateDate(now);
			tokenEntity.setExpireDate(expireTime);

			//保存token
			mapper.insert(tokenEntity);
		}else{
			//判断token是否过期
			if(tokenEntity.getExpireDate().getTime() < System.currentTimeMillis()){
				//token过期，重新生成token
				token = TokenGenerator.generateValue();
			}else {
				token = tokenEntity.getToken();
			}

			tokenEntity.setToken(token);
			tokenEntity.setUpdateDate(now);
			tokenEntity.setExpireDate(expireTime);

			//更新token
			this.updateById(tokenEntity);
		}

		Map<String, Object> map = new HashMap<>(2);
		map.put(Constant.TOKEN_HEADER, token);
		map.put("expire", EXPIRE);
		return Result.success(map);
	}

	@Override
	public void logout(Long userId) {
		//生成一个token
		String token = TokenGenerator.generateValue();
		//修改token
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq(SysUserTokenEntity::getUserId,userId);
		SysUserTokenEntity sysUserTokenEntity = mapper.selectOneByQuery(queryWrapper);
		sysUserTokenEntity.setToken(token);
		mapper.update(sysUserTokenEntity);
	}
}
