package com.easy.modules.sys.service;


import com.easy.common.page.PageData;
import com.easy.modules.sys.model.dto.SysRoleDTO;
import com.easy.modules.sys.entity.SysRoleEntity;
import com.easy.modules.sys.model.qo.DeleteQo;
import com.easy.modules.sys.model.qo.RoleListQo;
import com.mybatisflex.core.service.IService;

import java.util.List;
import java.util.Map;


/**
 * 角色
 *
 * @author Maw
 */
public interface SysRoleService extends IService<SysRoleEntity> {

	PageData<SysRoleDTO> pageList(RoleListQo qo);

	SysRoleDTO get(Long id);

	void save(SysRoleDTO dto);

	void update(SysRoleDTO dto);

	void delete(DeleteQo qo);

	List<SysRoleDTO> roleList();

}
