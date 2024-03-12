package com.easy.modules.log.service.impl;

import com.easy.common.page.PageData;
import com.easy.common.utils.ConvertUtils;
import com.easy.modules.log.dao.SysLogLoginDao;
import com.easy.modules.log.dto.SysLogLoginDTO;
import com.easy.modules.log.entity.SysLogLoginEntity;
import com.easy.modules.log.model.qo.LogLoginQo;
import com.easy.modules.log.service.SysLogLoginService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 登录日志
 *
 * @author Maw
 */
@Service
public class SysLogLoginServiceImpl extends ServiceImpl<SysLogLoginDao, SysLogLoginEntity> implements SysLogLoginService {

    @Override
    public PageData<SysLogLoginDTO> page(LogLoginQo qo) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.orderBy(SysLogLoginEntity::getCreateDate).desc();
        Page<SysLogLoginEntity> paginate = mapper.paginate(qo.getPage(), qo.getLimit(), queryWrapper);
        return new PageData<>(ConvertUtils.sourceToTarget(paginate.getRecords(),SysLogLoginDTO.class),paginate.getTotalRow());
    }
}
