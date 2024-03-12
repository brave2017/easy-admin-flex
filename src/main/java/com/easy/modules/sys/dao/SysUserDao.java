package com.easy.modules.sys.dao;

import com.easy.modules.sys.entity.SysUserEntity;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统用户
 *
 * @author Maw
 */
@Mapper
public interface SysUserDao extends BaseMapper<SysUserEntity> {

}
