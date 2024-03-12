package com.easy.modules.sys.dao;

import com.easy.modules.sys.entity.SysRoleUserEntity;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色用户关系
 *
 * @author Maw
 * @since 1.0.0
 */
@Mapper
public interface SysRoleUserDao extends BaseMapper<SysRoleUserEntity> {

}
