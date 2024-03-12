package com.easy.modules.sys.service.impl;

import cn.hutool.core.util.StrUtil;
import com.easy.common.exception.ErrorCode;
import com.easy.common.exception.ServiceException;
import com.easy.common.page.PageData;
import com.easy.common.utils.ConvertUtils;
import com.easy.common.utils.JsonUtils;
import com.easy.modules.sys.dao.SysParamsDao;
import com.easy.modules.sys.entity.SysParamsEntity;
import com.easy.modules.sys.entity.table.SysParamsEntityTableDef;
import com.easy.modules.sys.model.dto.SysParamsDTO;
import com.easy.modules.sys.model.qo.DeleteQo;
import com.easy.modules.sys.model.qo.ParamsQo;
import com.easy.modules.sys.redis.SysParamsRedis;
import com.easy.modules.sys.service.SysParamsService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.util.StringUtil;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 参数管理
 *
 * @author Maw
 * @since 1.0.0
 */
@Service
@AllArgsConstructor
public class SysParamsServiceImpl extends ServiceImpl<SysParamsDao, SysParamsEntity> implements SysParamsService {
    private final SysParamsRedis sysParamsRedis;

    @Override
    public PageData<SysParamsDTO> page(ParamsQo qo) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select(SysParamsEntityTableDef.SYS_PARAMS_ENTITY.ALL_COLUMNS)
                .where(SysParamsEntityTableDef.SYS_PARAMS_ENTITY.PARAM_CODE.like(qo.getParamCode(), StringUtil.isNotBlank(qo.getParamCode())));
        Page<SysParamsEntity> paginate = mapper.paginate(qo.getPage(), qo.getLimit(), queryWrapper);
        return new PageData<>(ConvertUtils.sourceToTarget(paginate.getRecords(),SysParamsDTO.class),paginate.getTotalRow());
    }

    @Override
    public SysParamsDTO get(Long id) {
        SysParamsEntity entity = mapper.selectOneById(id);

        return ConvertUtils.sourceToTarget(entity, SysParamsDTO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysParamsDTO dto) {
        SysParamsEntity entity = ConvertUtils.sourceToTarget(dto, SysParamsEntity.class);
        mapper.insert(entity);
        sysParamsRedis.set(entity.getParamCode(), entity.getParamValue());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysParamsDTO dto) {
        SysParamsEntity entity = ConvertUtils.sourceToTarget(dto, SysParamsEntity.class);
        updateById(entity);

        sysParamsRedis.set(entity.getParamCode(), entity.getParamValue());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(DeleteQo qo) {
        List<SysParamsEntity> list = QueryChain.of(mapper)
                .select(SysParamsEntityTableDef.SYS_PARAMS_ENTITY.PARAM_CODE)
                .where(SysParamsEntityTableDef.SYS_PARAMS_ENTITY.ID.in(qo.getIds()))
                .list();

        if(CollectionUtils.isEmpty(list)){
            return;
        }
        List<String> paramCodeList = list.stream().map(SysParamsEntity::getParamCode).collect(Collectors.toList());
        String[] paramCodes = paramCodeList.toArray(new String[list.size()]);
        sysParamsRedis.delete(paramCodes);

        //删除
        mapper.deleteBatchByIds(qo.getIds());
    }

    @Override
    public String getValue(String paramCode) {
        String paramValue = sysParamsRedis.get(paramCode);
        if (paramValue == null) {
            SysParamsEntity one = QueryChain.of(mapper).select(SysParamsEntityTableDef.SYS_PARAMS_ENTITY.ALL_COLUMNS)
                    .where(SysParamsEntityTableDef.SYS_PARAMS_ENTITY.PARAM_CODE.eq(paramCode))
                    .one();
            sysParamsRedis.set(one.getParamCode(), paramValue);
        }
        return paramValue;
    }

    @Override
    public <T> T getValueObject(String paramCode, Class<T> clazz) {
        String paramValue = getValue(paramCode);
        if (StrUtil.isNotBlank(paramValue)) {
            return JsonUtils.parseObject(paramValue, clazz);
        }

        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new ServiceException(ErrorCode.PARAMS_GET_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateValueByCode(String paramCode, String paramValue) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(SysParamsEntity::getParamCode,paramCode);
        SysParamsEntity sysParamsEntity = mapper.selectOneByQuery(queryWrapper);
        sysParamsEntity.setParamValue(paramValue);
        int count = mapper.update(sysParamsEntity);
        sysParamsRedis.set(paramCode, paramValue);
        return count;
    }

}
