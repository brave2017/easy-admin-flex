package com.easy.modules.sys.service;

import com.easy.common.page.PageData;
import com.easy.modules.sys.model.dto.SysDictDataDTO;
import com.easy.modules.sys.entity.SysDictDataEntity;
import com.easy.modules.sys.model.qo.DictDataQo;
import com.mybatisflex.core.service.IService;

import java.util.List;
import java.util.Map;

/**
 * 数据字典
 *
 * @author Maw
 */
public interface SysDictDataService extends IService<SysDictDataEntity> {

    PageData<SysDictDataDTO> pageList(DictDataQo qo);

    SysDictDataDTO get(Long id);

    void save(SysDictDataDTO dto);

    void update(SysDictDataDTO dto);

    void delete(List<Long> ids);

}
