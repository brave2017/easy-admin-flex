package com.easy.modules.sys.service;

import com.easy.modules.sys.model.dto.SysDeptDTO;
import com.easy.modules.sys.entity.SysDeptEntity;
import com.mybatisflex.core.service.IService;

import java.util.List;
import java.util.Map;

/**
 * 部门管理
 *
 * @author Maw
 */
public interface SysDeptService extends IService<SysDeptEntity> {

	List<SysDeptDTO> list(Map<String, Object> params);

	SysDeptDTO get(Long id);

	void save(SysDeptDTO dto);

	void update(SysDeptDTO dto);

	void delete(Long id);

	/**
	 * 根据部门ID，获取本部门及子部门ID列表
	 * @param id   部门ID
	 */
	List<Long> getSubDeptIdList(Long id);
}
