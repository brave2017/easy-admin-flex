package com.easy.modules.sys.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.easy.modules.sys.dao.SysRoleMenuDao;
import com.easy.modules.sys.entity.SysRoleMenuEntity;
import com.easy.modules.sys.entity.table.SysRoleMenuEntityTableDef;
import com.easy.modules.sys.service.SysRoleMenuService;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 角色与菜单对应关系
 *
 * @author Maw
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuDao, SysRoleMenuEntity> implements SysRoleMenuService {

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveOrUpdate(Long roleId, List<Long> menuIdList) {
		//先删除角色菜单关系
		deleteByRoleIds(Collections.singletonList(roleId));

		//角色没有一个菜单权限的情况
		if(CollUtil.isEmpty(menuIdList)){
			return ;
		}

		//保存角色菜单关系
		for(Long menuId : menuIdList){
			SysRoleMenuEntity sysRoleMenuEntity = new SysRoleMenuEntity();
			sysRoleMenuEntity.setMenuId(menuId);
			sysRoleMenuEntity.setRoleId(roleId);

			//保存
			mapper.insert(sysRoleMenuEntity);
		}
	}

	@Override
	public List<Long> getMenuIdList(Long roleId){
		// select menu_id from sys_role_menu where role_id = #{value}
		List<SysRoleMenuEntity> list = QueryChain.of(mapper).select(SysRoleMenuEntityTableDef.SYS_ROLE_MENU_ENTITY.ALL_COLUMNS)
				.where(SysRoleMenuEntityTableDef.SYS_ROLE_MENU_ENTITY.ROLE_ID.eq(roleId))
				.list();
		return list.stream().map(SysRoleMenuEntity::getMenuId).collect(Collectors.toList());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteByRoleIds(List<Long> roleIds) {
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.where(SysRoleMenuEntityTableDef.SYS_ROLE_MENU_ENTITY.ROLE_ID.in(roleIds));
		mapper.deleteByQuery(queryWrapper);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteByMenuId(Long menuId) {
		// delete from sys_role_menu where menu_id = #{value}
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq(SysRoleMenuEntity::getMenuId,menuId);
		mapper.deleteByQuery(queryWrapper);
	}

}
