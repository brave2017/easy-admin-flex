package com.easy.modules.sys.dao;

import com.easy.modules.sys.entity.SysDeptEntity;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 部门管理
 *
 * @author Maw
 */
@Mapper
public interface SysDeptDao extends BaseMapper<SysDeptEntity> {

}
