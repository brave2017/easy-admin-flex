package com.easy.modules.sys.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.easy.modules.sys.dao.SysRoleUserDao;
import com.easy.modules.sys.entity.SysRoleUserEntity;
import com.easy.modules.sys.entity.table.SysRoleUserEntityTableDef;
import com.easy.modules.sys.service.SysRoleUserService;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色用户关系
 *
 * @author Maw
 * @since 1.0.0
 */
@Service
public class SysRoleUserServiceImpl extends ServiceImpl<SysRoleUserDao, SysRoleUserEntity> implements SysRoleUserService {

    @Override
    public void saveOrUpdate(Long userId, List<Long> roleIdList) {
        //先删除角色用户关系
        deleteByUserIds(Collections.singletonList(userId));

        //用户没有一个角色权限的情况
        if(CollUtil.isEmpty(roleIdList)){
            return ;
        }

        //保存角色用户关系
        for(Long roleId : roleIdList){
            SysRoleUserEntity sysRoleUserEntity = new SysRoleUserEntity();
            sysRoleUserEntity.setUserId(userId);
            sysRoleUserEntity.setRoleId(roleId);

            //保存
            mapper.insert(sysRoleUserEntity);
        }
    }

    @Override
    public void deleteByRoleIds(List<Long> roleIds) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.where(SysRoleUserEntityTableDef.SYS_ROLE_USER_ENTITY.ROLE_ID.in(roleIds));
        mapper.deleteByQuery(queryWrapper);
    }

    @Override
    public void deleteByUserIds(List<Long> userIds) {
        // delete from sys_role_user where user_id in
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.in(SysRoleUserEntity::getUserId,userIds);
        mapper.deleteByQuery(queryWrapper);
    }

    @Override
    public List<Long> getRoleIdList(Long userId) {
        List<SysRoleUserEntity> list = QueryChain.of(mapper)
                .select(SysRoleUserEntityTableDef.SYS_ROLE_USER_ENTITY.ROLE_ID)
                .from(SysRoleUserEntityTableDef.SYS_ROLE_USER_ENTITY)
                .where(SysRoleUserEntityTableDef.SYS_ROLE_USER_ENTITY.USER_ID.eq(userId))
                .list();
        return list.stream().map(SysRoleUserEntity::getRoleId).collect(Collectors.toList());
    }
}
