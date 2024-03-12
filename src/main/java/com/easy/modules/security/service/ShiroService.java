package com.easy.modules.security.service;

import com.easy.modules.security.entity.SysUserTokenEntity;
import com.easy.modules.security.user.UserDetail;
import com.easy.modules.sys.entity.SysUserEntity;

import java.util.List;
import java.util.Set;

/**
 * shiro相关接口
 *
 * @author Maw
 */
public interface ShiroService {
    /**
     * 获取用户权限列表
     */
    Set<String> getUserPermissions(UserDetail user);

    SysUserTokenEntity getByToken(String token);

    /**
     * 根据用户ID，查询用户
     * @param userId
     */
    SysUserEntity getUser(Long userId);

    /**
     * 获取用户对应的部门数据权限
     * @param userId  用户ID
     * @return        返回部门ID列表
     */
    List<Long> getDataScopeList(Long userId);
}
