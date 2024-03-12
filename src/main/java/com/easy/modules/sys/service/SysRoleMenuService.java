package com.easy.modules.sys.service;

import com.easy.modules.sys.entity.SysRoleMenuEntity;
import com.mybatisflex.core.service.IService;

import java.util.List;


/**
 * 角色与菜单对应关系
 *
 * @author Maw
 */
public interface SysRoleMenuService extends IService<SysRoleMenuEntity> {

	/**
	 * 根据角色ID，获取菜单ID列表
	 */
	List<Long> getMenuIdList(Long roleId);

	/**
	 * 保存或修改
	 * @param roleId      角色ID
	 * @param menuIdList  菜单ID列表
	 */
	void saveOrUpdate(Long roleId, List<Long> menuIdList);

	/**
	 * 根据角色id，删除角色菜单关系
	 * @param roleIds 角色ids
	 */
	void deleteByRoleIds(List<Long> roleIds);

	/**
	 * 根据菜单id，删除角色菜单关系
	 * @param menuId 菜单id
	 */
	void deleteByMenuId(Long menuId);
}
