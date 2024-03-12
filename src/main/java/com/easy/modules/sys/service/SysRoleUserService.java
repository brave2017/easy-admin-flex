package com.easy.modules.sys.service;

import com.easy.modules.sys.entity.SysRoleUserEntity;
import com.mybatisflex.core.service.IService;

import java.util.List;

/**
 * 角色用户关系
 *
 * @author Maw
 * @since 1.0.0
 */
public interface SysRoleUserService extends IService<SysRoleUserEntity> {

    /**
     * 保存或修改
     * @param userId      用户ID
     * @param roleIdList  角色ID列表
     */
    void saveOrUpdate(Long userId, List<Long> roleIdList);

    /**
     * 根据角色ids，删除角色用户关系
     * @param roleIds 角色ids
     */
    void deleteByRoleIds(List<Long> roleIds);

    /**
     * 根据用户id，删除角色用户关系
     * @param userIds 用户ids
     */
    void deleteByUserIds(List<Long> userIds);

    /**
     * 角色ID列表
     * @param userId  用户ID
     */
    List<Long> getRoleIdList(Long userId);
}
