package com.easy.modules.sys.service;

import com.easy.common.page.PageData;
import com.easy.modules.sys.model.dto.SysDictTypeDTO;
import com.easy.modules.sys.entity.DictType;
import com.easy.modules.sys.entity.SysDictTypeEntity;
import com.easy.modules.sys.model.qo.DeleteQo;
import com.easy.modules.sys.model.qo.DictTypeQo;
import com.mybatisflex.core.service.IService;

import java.util.List;
import java.util.Map;

/**
 * 数据字典
 *
 * @author Maw
 */
public interface SysDictTypeService extends IService<SysDictTypeEntity> {

    PageData<SysDictTypeDTO> page(DictTypeQo params);

    SysDictTypeDTO get(Long id);

    void save(SysDictTypeDTO dto);

    void update(SysDictTypeDTO dto);

    void delete(DeleteQo qo);

    /**
     * 获取所有字典
     */
    List<DictType> getAllList();

}
