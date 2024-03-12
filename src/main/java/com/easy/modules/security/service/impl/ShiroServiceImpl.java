package com.easy.modules.security.service.impl;

import cn.hutool.core.util.StrUtil;
import com.easy.modules.security.dao.SysUserTokenDao;
import com.easy.modules.security.entity.SysUserTokenEntity;
import com.easy.modules.security.service.ShiroService;
import com.easy.modules.security.user.UserDetail;
import com.easy.modules.sys.dao.SysMenuDao;
import com.easy.modules.sys.dao.SysRoleDataScopeDao;
import com.easy.modules.sys.dao.SysUserDao;
import com.easy.modules.sys.entity.SysMenuEntity;
import com.easy.modules.sys.entity.SysRoleUserEntity;
import com.easy.modules.sys.entity.SysUserEntity;
import com.easy.modules.sys.entity.table.SysMenuEntityTableDef;
import com.easy.modules.sys.entity.table.SysRoleDataScopeEntityTableDef;
import com.easy.modules.sys.entity.table.SysRoleMenuEntityTableDef;
import com.easy.modules.sys.entity.table.SysRoleUserEntityTableDef;
import com.easy.modules.sys.enums.SuperAdminEnum;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.row.Db;
import com.mybatisflex.core.row.Row;
import com.mybatisflex.core.row.RowUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ShiroServiceImpl implements ShiroService {
    private final SysMenuDao sysMenuDao;
    private final SysUserDao sysUserDao;
    private final SysUserTokenDao sysUserTokenDao;
    private final SysRoleDataScopeDao sysRoleDataScopeDao;

    @Override
    public Set<String> getUserPermissions(UserDetail user) {
        //系统管理员，拥有最高权限
        List<String> permissionsList;
        if (Objects.equals(user.getSuperAdmin(), SuperAdminEnum.YES.getValue())) {
            List<SysMenuEntity> list = sysMenuDao.selectAll();
            permissionsList = list.stream().map(SysMenuEntity::getPermissions).distinct().collect(Collectors.toList());
        } else {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.select(SysMenuEntityTableDef.SYS_MENU_ENTITY.PERMISSIONS)
                    .from(SysRoleUserEntityTableDef.SYS_ROLE_USER_ENTITY)
                    .leftJoin(SysRoleMenuEntityTableDef.SYS_ROLE_MENU_ENTITY).on(SysRoleUserEntityTableDef.SYS_ROLE_USER_ENTITY.ROLE_ID.eq(SysRoleMenuEntityTableDef.SYS_ROLE_MENU_ENTITY.ROLE_ID))
                    .leftJoin(SysMenuEntityTableDef.SYS_MENU_ENTITY).on(SysRoleMenuEntityTableDef.SYS_ROLE_MENU_ENTITY.MENU_ID.eq(SysMenuEntityTableDef.SYS_MENU_ENTITY.ID))
                    .where(SysRoleUserEntityTableDef.SYS_ROLE_USER_ENTITY.USER_ID.eq(user.getId()))
                    .orderBy(SysMenuEntityTableDef.SYS_MENU_ENTITY.SORT.asc());
            List<Row> rows = Db.selectListByQuery(queryWrapper);
            permissionsList = RowUtil.toEntityList(rows, String.class);
        }

        //用户权限列表
        Set<String> permsSet = new HashSet<>();
        for (String permissions : permissionsList) {
            if (StrUtil.isBlank(permissions)) {
                continue;
            }
            permsSet.addAll(Arrays.asList(permissions.trim().split(",")));
        }

        return permsSet;
    }

    @Override
    public SysUserTokenEntity getByToken(String token) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq(SysUserTokenEntity::getToken,token);
        return sysUserTokenDao.selectOneByQuery(wrapper);
    }

    @Override
    public SysUserEntity getUser(Long userId) {
        return sysUserDao.selectOneById(userId);
    }

    @Override
    public List<Long> getDataScopeList(Long userId) {
        // select t2.dept_id from sys_role_user t1, sys_role_data_scope t2
        //          where t1.user_id = #{value} and t1.role_id = t2.role_id
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select(SysRoleUserEntityTableDef.SYS_ROLE_USER_ENTITY.ALL_COLUMNS)
                .from(SysRoleUserEntityTableDef.SYS_ROLE_USER_ENTITY)
                .join(SysRoleDataScopeEntityTableDef.SYS_ROLE_DATA_SCOPE_ENTITY).on(SysRoleUserEntityTableDef.SYS_ROLE_USER_ENTITY.ROLE_ID.eq(SysRoleDataScopeEntityTableDef.SYS_ROLE_DATA_SCOPE_ENTITY.ROLE_ID))
                .where(SysRoleUserEntityTableDef.SYS_ROLE_USER_ENTITY.USER_ID.eq(userId));
        List<Row> rows = Db.selectListByQuery(queryWrapper);
        return RowUtil.toEntityList(rows, Long.class);
    }
}
