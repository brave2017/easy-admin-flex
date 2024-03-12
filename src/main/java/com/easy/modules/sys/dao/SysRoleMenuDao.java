package com.easy.modules.sys.dao;

import com.easy.modules.sys.entity.SysRoleMenuEntity;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色与菜单对应关系
 *
 * @author Maw
 */
@Mapper
public interface SysRoleMenuDao extends BaseMapper<SysRoleMenuEntity> {

}
