package com.easy.modules.security.dao;

import com.easy.modules.security.entity.SysUserTokenEntity;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统用户Token
 *
 * @author Maw
 */
@Mapper
public interface SysUserTokenDao extends BaseMapper<SysUserTokenEntity> {

}
