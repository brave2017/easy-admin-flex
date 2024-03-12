package com.easy.modules.log.dao;

import com.easy.modules.log.entity.SysLogOperationEntity;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 操作日志
 *
 * @author Maw
 * @since 1.0.0
 */
@Mapper
public interface SysLogOperationDao extends BaseMapper<SysLogOperationEntity> {

}
