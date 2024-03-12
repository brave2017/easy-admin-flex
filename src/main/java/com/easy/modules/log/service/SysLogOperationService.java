package com.easy.modules.log.service;

import com.easy.common.page.PageData;
import com.easy.modules.log.dto.SysLogOperationDTO;
import com.easy.modules.log.entity.SysLogOperationEntity;
import com.easy.modules.log.model.qo.LogOperationQo;
import com.mybatisflex.core.service.IService;

import java.util.List;
import java.util.Map;

/**
 * 操作日志
 *
 * @author Maw
 * @since 1.0.0
 */
public interface SysLogOperationService extends IService<SysLogOperationEntity> {

    PageData<SysLogOperationDTO> page(LogOperationQo qo);
}
