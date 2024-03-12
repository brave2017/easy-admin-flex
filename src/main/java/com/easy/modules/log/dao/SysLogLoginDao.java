package com.easy.modules.log.dao;

import com.easy.modules.log.entity.SysLogLoginEntity;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 登录日志
 *
 * @author Maw
 * @since 1.0.0
 */
@Mapper
public interface SysLogLoginDao extends BaseMapper<SysLogLoginEntity> {

}
