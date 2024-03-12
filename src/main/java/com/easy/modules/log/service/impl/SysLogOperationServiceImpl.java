package com.easy.modules.log.service.impl;

import com.easy.common.page.PageData;
import com.easy.common.utils.ConvertUtils;
import com.easy.modules.log.dao.SysLogOperationDao;
import com.easy.modules.log.dto.SysLogOperationDTO;
import com.easy.modules.log.entity.SysLogOperationEntity;
import com.easy.modules.log.model.qo.LogOperationQo;
import com.easy.modules.log.service.SysLogOperationService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 操作日志
 *
 * @author Maw
 * @since 1.0.0
 */
@Service
public class SysLogOperationServiceImpl extends ServiceImpl<SysLogOperationDao, SysLogOperationEntity> implements SysLogOperationService {

    @Override
    public PageData<SysLogOperationDTO> page(LogOperationQo qo) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.orderBy(SysLogOperationEntity::getCreateDate).desc();
        Page<SysLogOperationEntity> paginate = mapper.paginate(qo.getPage(), qo.getLimit(), queryWrapper);
        return new PageData<>(ConvertUtils.sourceToTarget(paginate.getRecords(),SysLogOperationDTO.class),paginate.getTotalRow());
    }
}
