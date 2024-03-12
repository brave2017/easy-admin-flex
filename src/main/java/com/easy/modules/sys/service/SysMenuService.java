package com.easy.modules.sys.service;

import com.easy.modules.security.user.UserDetail;
import com.easy.modules.sys.model.dto.SysMenuDTO;
import com.easy.modules.sys.entity.SysMenuEntity;
import com.mybatisflex.core.service.IService;

import java.util.List;


/**
 * 菜单管理
 *
 * @author Maw
 */
public interface SysMenuService extends IService<SysMenuEntity> {

	SysMenuDTO get(Long id);

	void save(SysMenuDTO dto);

	void update(SysMenuDTO dto);

	void delete(Long id);

	/**
	 * 菜单列表
	 *
	 * @param menuType 菜单类型
	 */
	List<SysMenuDTO> getAllMenuList(Integer menuType);

	/**
	 * 用户菜单列表
	 *
	 * @param user  用户
	 * @param menuType 菜单类型
	 */
	List<SysMenuDTO> getUserMenuList(UserDetail user, Integer menuType);

	/**
	 * 根据父菜单，查询子菜单
	 * @param pid  父菜单ID
	 */
	List<SysMenuDTO> getListPid(Long pid);
}
