package com.easy.modules.log.service;

import com.easy.common.page.PageData;
import com.easy.modules.log.dto.SysLogLoginDTO;
import com.easy.modules.log.entity.SysLogLoginEntity;
import com.easy.modules.log.model.qo.LogLoginQo;
import com.mybatisflex.core.service.IService;

/**
 * 登录日志
 *
 * @author Maw
 * @since 1.0.0
 */
public interface SysLogLoginService extends IService<SysLogLoginEntity> {
    PageData<SysLogLoginDTO> page(LogLoginQo qo);
}
