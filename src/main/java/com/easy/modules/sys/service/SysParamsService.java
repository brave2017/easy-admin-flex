package com.easy.modules.sys.service;


import com.easy.common.page.PageData;
import com.easy.modules.sys.model.dto.SysParamsDTO;
import com.easy.modules.sys.entity.SysParamsEntity;
import com.easy.modules.sys.model.qo.DeleteQo;
import com.easy.modules.sys.model.qo.ParamsQo;
import com.mybatisflex.core.service.IService;

import java.util.List;
import java.util.Map;

/**
 * 参数管理
 *
 * @author Maw
 * @since 1.0.0
 */
public interface SysParamsService extends IService<SysParamsEntity> {

    PageData<SysParamsDTO> page(ParamsQo qo);

    SysParamsDTO get(Long id);

    void save(SysParamsDTO dto);

    void update(SysParamsDTO dto);

    void delete(DeleteQo qo);

    /**
     * 根据参数编码，获取参数的value值
     *
     * @param paramCode  参数编码
     */
    String getValue(String paramCode);

    /**
     * 根据参数编码，获取value的Object对象
     * @param paramCode  参数编码
     * @param clazz  Object对象
     */
    <T> T getValueObject(String paramCode, Class<T> clazz);

    /**
     * 根据参数编码，更新value
     * @param paramCode  参数编码
     * @param paramValue  参数值
     */
    int updateValueByCode(String paramCode, String paramValue);
}
