package com.easy.modules.log.service;


import com.easy.common.page.PageData;
import com.easy.modules.log.dto.SysLogErrorDTO;
import com.easy.modules.log.entity.SysLogErrorEntity;
import com.easy.modules.log.model.qo.LogErrorQo;
import com.mybatisflex.core.service.IService;

/**
 * 异常日志
 *
 * @author Maw
 * @since 1.0.0
 */
public interface SysLogErrorService extends IService<SysLogErrorEntity> {
    PageData<SysLogErrorDTO> page(LogErrorQo qo);

}
