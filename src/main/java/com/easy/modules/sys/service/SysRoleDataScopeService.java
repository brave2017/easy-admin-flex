package com.easy.modules.sys.service;

import com.easy.modules.sys.entity.SysRoleDataScopeEntity;
import com.mybatisflex.core.service.IService;

import java.util.List;

/**
 * 角色数据权限
 *
 * @author Maw
 * @since 1.0.0
 */
public interface SysRoleDataScopeService extends IService<SysRoleDataScopeEntity> {

    /**
     * 根据角色ID，获取部门ID列表
     */
    List<Long> getDeptIdList(Long roleId);

    /**
     * 保存或修改
     * @param roleId      角色ID
     * @param deptIdList  部门ID列表
     */
    void saveOrUpdate(Long roleId, List<Long> deptIdList);

    /**
     * 根据角色id，删除角色数据权限关系
     * @param roleId 角色ids
     */
    void deleteByRoleIds(List<Long> roleId);
}
