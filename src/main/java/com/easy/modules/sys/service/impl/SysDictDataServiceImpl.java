package com.easy.modules.sys.service.impl;

import com.easy.common.page.PageData;
import com.easy.common.utils.ConvertUtils;
import com.easy.modules.sys.dao.SysDictDataDao;
import com.easy.modules.sys.entity.SysDictDataEntity;
import com.easy.modules.sys.entity.table.SysDictDataEntityTableDef;
import com.easy.modules.sys.model.dto.SysDictDataDTO;
import com.easy.modules.sys.model.qo.DictDataQo;
import com.easy.modules.sys.service.SysDictDataService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.util.StringUtil;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 字典类型
 *
 * @author Maw
 */
@Service
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataDao, SysDictDataEntity> implements SysDictDataService {

    @Override
    public PageData<SysDictDataDTO> pageList(DictDataQo qo) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select(SysDictDataEntityTableDef.SYS_DICT_DATA_ENTITY.ALL_COLUMNS)
                .where(SysDictDataEntityTableDef.SYS_DICT_DATA_ENTITY.DICT_LABEL.like(qo.getDictLabel(),StringUtil.isNotBlank(qo.getDictLabel())))
                .and(SysDictDataEntityTableDef.SYS_DICT_DATA_ENTITY.DICT_VALUE.like(qo.getDictValue(),StringUtil.isNotBlank(qo.getDictValue())))
                .and(SysDictDataEntityTableDef.SYS_DICT_DATA_ENTITY.DICT_TYPE_ID.eq(qo.getDictTypeId()));
        Page<SysDictDataEntity> paginate = mapper.paginate(qo.getPage(), qo.getLimit(), queryWrapper);
        List<SysDictDataDTO> list = ConvertUtils.sourceToTarget(paginate.getRecords(), SysDictDataDTO.class);
        return new PageData<>(list,paginate.getTotalRow());
    }

    @Override
    public SysDictDataDTO get(Long id) {
        SysDictDataEntity entity = mapper.selectOneById(id);
        return ConvertUtils.sourceToTarget(entity, SysDictDataDTO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysDictDataDTO dto) {
        SysDictDataEntity entity = ConvertUtils.sourceToTarget(dto, SysDictDataEntity.class);
        mapper.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysDictDataDTO dto) {
        SysDictDataEntity entity = ConvertUtils.sourceToTarget(dto, SysDictDataEntity.class);
        updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> ids) {
        mapper.deleteBatchByIds(ids);
    }

}
