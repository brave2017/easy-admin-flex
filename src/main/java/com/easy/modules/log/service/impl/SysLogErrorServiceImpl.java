package com.easy.modules.log.service.impl;

import com.easy.common.page.PageData;
import com.easy.common.utils.ConvertUtils;
import com.easy.modules.log.dao.SysLogErrorDao;
import com.easy.modules.log.dto.SysLogErrorDTO;
import com.easy.modules.log.entity.SysLogErrorEntity;
import com.easy.modules.log.model.qo.LogErrorQo;
import com.easy.modules.log.service.SysLogErrorService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 异常日志
 *
 * @author Maw
 * @since 1.0.0
 */
@Service
public class SysLogErrorServiceImpl extends ServiceImpl<SysLogErrorDao, SysLogErrorEntity> implements SysLogErrorService {

    @Override
    public PageData<SysLogErrorDTO> page(LogErrorQo qo) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.orderBy(SysLogErrorEntity::getCreateDate).desc();
        Page<SysLogErrorEntity> paginate = mapper.paginate(qo.getPage(), qo.getLimit(), queryWrapper);
        return new PageData<>(ConvertUtils.sourceToTarget(paginate.getRecords(),SysLogErrorDTO.class),paginate.getTotalRow());
    }

}
