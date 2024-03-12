package com.easy.modules.sys.service;

import com.easy.common.page.PageData;
import com.easy.modules.sys.model.dto.SysUserDTO;
import com.easy.modules.sys.entity.SysUserEntity;
import com.easy.modules.sys.model.qo.DeleteQo;
import com.easy.modules.sys.model.qo.UserListQo;
import com.mybatisflex.core.service.IService;

import java.util.List;


/**
 * 系统用户
 *
 * @author Maw
 */
public interface SysUserService extends IService<SysUserEntity> {

	PageData<SysUserDTO> pageList(UserListQo params);

	SysUserDTO getById(Long id);

	SysUserDTO getByUsername(String username);

	void save(SysUserDTO dto);

	void update(SysUserDTO dto);

	void delete(List<Long> userIds);

	/**
	 * 修改密码
	 * @param id           用户ID
	 * @param newPassword  新密码
	 */
	void updatePassword(Long id, String newPassword);

    void deleteBatchIds(DeleteQo qo);
}
