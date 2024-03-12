package com.easy.modules.log.dao;

import com.easy.modules.log.entity.SysLogErrorEntity;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 异常日志
 *
 * @author Maw
 * @since 1.0.0
 */
@Mapper
public interface SysLogErrorDao extends BaseMapper<SysLogErrorEntity> {

}
