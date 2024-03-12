package com.easy.modules.sys.service.impl;

import com.easy.common.page.PageData;
import com.easy.common.utils.ConvertUtils;
import com.easy.modules.sys.dao.SysDictDataDao;
import com.easy.modules.sys.dao.SysDictTypeDao;
import com.easy.modules.sys.model.dto.SysDictTypeDTO;
import com.easy.modules.sys.entity.DictData;
import com.easy.modules.sys.entity.DictType;
import com.easy.modules.sys.entity.SysDictDataEntity;
import com.easy.modules.sys.entity.SysDictTypeEntity;
import com.easy.modules.sys.entity.table.SysDictDataEntityTableDef;
import com.easy.modules.sys.entity.table.SysDictTypeEntityTableDef;
import com.easy.modules.sys.model.qo.DeleteQo;
import com.easy.modules.sys.model.qo.DictTypeQo;
import com.easy.modules.sys.service.SysDictTypeService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.util.StringUtil;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 字典类型
 *
 * @author Maw
 */
@Service
@AllArgsConstructor
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeDao, SysDictTypeEntity> implements SysDictTypeService {
    private final SysDictDataDao sysDictDataDao;

    @Override
    public PageData<SysDictTypeDTO> page(DictTypeQo qo) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select(SysDictTypeEntityTableDef.SYS_DICT_TYPE_ENTITY.ALL_COLUMNS)
                .where(SysDictTypeEntityTableDef.SYS_DICT_TYPE_ENTITY.DICT_TYPE.like(qo.getDictType(),StringUtil.isNotBlank(qo.getDictType())))
                .and(SysDictTypeEntityTableDef.SYS_DICT_TYPE_ENTITY.DICT_NAME.like(qo.getDictName(),StringUtil.isNotBlank(qo.getDictName())));
        Page<SysDictTypeEntity> paginate = mapper.paginate(qo.getPage(), qo.getLimit(), queryWrapper);
        return new PageData<>(ConvertUtils.sourceToTarget(paginate.getRecords(),SysDictTypeDTO.class),paginate.getTotalRow());
    }

    @Override
    public SysDictTypeDTO get(Long id) {
        SysDictTypeEntity entity = mapper.selectOneById(id);

        return ConvertUtils.sourceToTarget(entity, SysDictTypeDTO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysDictTypeDTO dto) {
        SysDictTypeEntity entity = ConvertUtils.sourceToTarget(dto, SysDictTypeEntity.class);

        mapper.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysDictTypeDTO dto) {
        SysDictTypeEntity entity = ConvertUtils.sourceToTarget(dto, SysDictTypeEntity.class);

        updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(DeleteQo qo) {
        mapper.deleteBatchByIds(qo.getIds());
    }

    @Override
    public List<DictType> getAllList() {

        List<SysDictTypeEntity> dictTypeList = QueryChain.of(mapper)
                .select(SysDictTypeEntityTableDef.SYS_DICT_TYPE_ENTITY.ALL_COLUMNS)
                .orderBy(SysDictTypeEntityTableDef.SYS_DICT_TYPE_ENTITY.DICT_TYPE.getName(), SysDictTypeEntityTableDef.SYS_DICT_TYPE_ENTITY.SORT.getName())
                .list();

        List<SysDictDataEntity> dictDataList = QueryChain.of(sysDictDataDao)
                .select(SysDictDataEntityTableDef.SYS_DICT_DATA_ENTITY.ALL_COLUMNS)
                .orderBy(SysDictDataEntityTableDef.SYS_DICT_DATA_ENTITY.DICT_TYPE_ID.getName(), SysDictDataEntityTableDef.SYS_DICT_DATA_ENTITY.SORT.getName())
                .list();

        List<DictType> typeList = dictTypeList.stream()
                .map(dictTypeEntity -> {
                    DictType dictType = new DictType();
                    dictType.setId(dictTypeEntity.getId());
                    dictType.setDictType(dictTypeEntity.getDictType());
                    return dictType;
                })
                .collect(Collectors.toList());

        List<DictData> dataList = dictDataList.stream()
                .map(dictDataEntity -> {
                    DictData data = new DictData();
                    data.setDictTypeId(dictDataEntity.getDictTypeId());
                    data.setDictLabel(dictDataEntity.getDictLabel());
                    data.setDictValue(dictDataEntity.getDictValue());
                    return data;
                })
                .collect(Collectors.toList());


        Map<Long, List<DictData>> groupedData = dataList.stream()
                .collect(Collectors.groupingBy(DictData::getDictTypeId));

        typeList.forEach(type -> type.getDataList().addAll(groupedData.getOrDefault(type.getId(), Collections.emptyList())));
        return typeList;
    }

}
