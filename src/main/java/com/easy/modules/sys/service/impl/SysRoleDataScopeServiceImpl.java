package com.easy.modules.sys.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.easy.modules.sys.dao.SysRoleDataScopeDao;
import com.easy.modules.sys.entity.SysRoleDataScopeEntity;
import com.easy.modules.sys.entity.table.SysRoleDataScopeEntityTableDef;
import com.easy.modules.sys.service.SysRoleDataScopeService;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色数据权限
 *
 * @author Maw
 * @since 1.0.0
 */
@Service
public class SysRoleDataScopeServiceImpl extends ServiceImpl<SysRoleDataScopeDao, SysRoleDataScopeEntity> implements SysRoleDataScopeService {

    @Override
    public List<Long> getDeptIdList(Long roleId) {
        List<SysRoleDataScopeEntity> list = QueryChain.of(mapper).select(SysRoleDataScopeEntityTableDef.SYS_ROLE_DATA_SCOPE_ENTITY.ALL_COLUMNS)
                .where(SysRoleDataScopeEntityTableDef.SYS_ROLE_DATA_SCOPE_ENTITY.ROLE_ID.eq(roleId))
                .list();
        return list.stream().map(SysRoleDataScopeEntity::getDeptId).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(Long roleId, List<Long> deptIdList) {
        //先删除角色数据权限关系
        deleteByRoleIds(Collections.singletonList(roleId));

        //角色没有一个数据权限的情况
        if(CollUtil.isEmpty(deptIdList)){
            return ;
        }

        //保存角色数据权限关系
        for(Long deptId : deptIdList){
            SysRoleDataScopeEntity sysRoleDataScopeEntity = new SysRoleDataScopeEntity();
            sysRoleDataScopeEntity.setDeptId(deptId);
            sysRoleDataScopeEntity.setRoleId(roleId);

            //保存
            mapper.insert(sysRoleDataScopeEntity);
        }
    }

    @Override
    public void deleteByRoleIds(List<Long> roleIds) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.where(SysRoleDataScopeEntityTableDef.SYS_ROLE_DATA_SCOPE_ENTITY.ROLE_ID.in(roleIds));
        mapper.deleteByQuery(queryWrapper);
    }
}
