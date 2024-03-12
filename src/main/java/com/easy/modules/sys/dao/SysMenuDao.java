package com.easy.modules.sys.dao;

import com.easy.modules.sys.entity.SysMenuEntity;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 菜单管理
 *
 * @author Maw
 */
@Mapper
public interface SysMenuDao extends BaseMapper<SysMenuEntity> {

}
