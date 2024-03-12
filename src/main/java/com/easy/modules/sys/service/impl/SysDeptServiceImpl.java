package com.easy.modules.sys.service.impl;

import com.easy.common.constant.Constant;
import com.easy.common.exception.ErrorCode;
import com.easy.common.exception.ServiceException;
import com.easy.common.utils.ConvertUtils;
import com.easy.common.utils.TreeUtils;
import com.easy.modules.security.user.SecurityUser;
import com.easy.modules.security.user.UserDetail;
import com.easy.modules.sys.dao.SysDeptDao;
import com.easy.modules.sys.dao.SysUserDao;
import com.easy.modules.sys.entity.table.SysDeptEntityTableDef;
import com.easy.modules.sys.entity.table.SysUserEntityTableDef;
import com.easy.modules.sys.model.dto.SysDeptDTO;
import com.easy.modules.sys.entity.SysDeptEntity;
import com.easy.modules.sys.enums.SuperAdminEnum;
import com.easy.modules.sys.service.SysDeptService;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.qiniu.util.StringUtils;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class SysDeptServiceImpl extends ServiceImpl<SysDeptDao, SysDeptEntity> implements SysDeptService {

    private final SysUserDao sysUserDao;

    @Override
    public List<SysDeptDTO> list(Map<String, Object> params) {
        //普通管理员，只能查询所属部门及子部门的数据
        UserDetail user = SecurityUser.getUser();
        List<Long> deptIdList = new ArrayList<>();
        if (Objects.equals(user.getSuperAdmin(), SuperAdminEnum.NO.getValue())) {
            deptIdList = getSubDeptIdList(user.getDeptId());
        }

        List<SysDeptEntity> entityList = QueryChain.of(mapper)
                .select(SysDeptEntityTableDef.SYS_DEPT_ENTITY.ALL_COLUMNS)
                .select("(SELECT t2.NAME FROM sys_dept t2 WHERE t2.id = t1.pid) AS parentName")
                .from(SysDeptEntityTableDef.SYS_DEPT_ENTITY.as("t1"))
                .where(SysDeptEntityTableDef.SYS_DEPT_ENTITY.ID.in(deptIdList, CollectionUtils::isNotEmpty))
                .orderBy(SysDeptEntityTableDef.SYS_DEPT_ENTITY.SORT.asc())
                .list();

        List<SysDeptDTO> dtoList = ConvertUtils.sourceToTarget(entityList, SysDeptDTO.class);
        return TreeUtils.build(dtoList);
    }

    @Override
    public SysDeptDTO get(Long id) {
        //超级管理员，部门ID为null
        if (id == null) {
            return null;
        }

        SysDeptEntity entity = QueryChain.of(mapper)
                .select(SysDeptEntityTableDef.SYS_DEPT_ENTITY.ALL_COLUMNS,SysDeptEntityTableDef.SYS_DEPT_ENTITY.NAME.as("parentName"))
                .from(SysDeptEntityTableDef.SYS_DEPT_ENTITY.as("t1"))
                .leftJoin(SysDeptEntityTableDef.SYS_DEPT_ENTITY).on(SysDeptEntityTableDef.SYS_DEPT_ENTITY.ID.eq(SysDeptEntityTableDef.SYS_DEPT_ENTITY.PID))
                .where(SysDeptEntityTableDef.SYS_DEPT_ENTITY.ID.eq(id))
                .one();

        return ConvertUtils.sourceToTarget(entity, SysDeptDTO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysDeptDTO dto) {
        SysDeptEntity entity = ConvertUtils.sourceToTarget(dto, SysDeptEntity.class);
        entity.setPids(getPidList(entity.getPid()));
        mapper.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysDeptDTO dto) {
        SysDeptEntity entity = ConvertUtils.sourceToTarget(dto, SysDeptEntity.class);

        //上级部门不能为自身
        if (entity.getId().equals(entity.getPid())) {
            throw new ServiceException(ErrorCode.SUPERIOR_DEPT_ERROR);
        }

        //上级部门不能为下级部门
        List<Long> subDeptList = getSubDeptIdList(entity.getId());
        if (subDeptList.contains(entity.getPid())) {
            throw new ServiceException(ErrorCode.SUPERIOR_DEPT_ERROR);
        }

        entity.setPids(getPidList(entity.getPid()));
        updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        //判断是否有子部门
        List<Long> subList = getSubDeptIdList(id);
        if (subList.size() > 1) {
            throw new ServiceException(ErrorCode.DEPT_SUB_DELETE_ERROR);
        }
        long count = QueryChain.of(sysUserDao)
                .where(SysUserEntityTableDef.SYS_USER_ENTITY.DEPT_ID.eq(id))
                .count();

        //判断部门下面是否有用户
        if (count > 0) {
            throw new ServiceException(ErrorCode.DEPT_USER_DELETE_ERROR);
        }
        mapper.deleteById(id);
    }

    @Override
    public List<Long> getSubDeptIdList(Long id) {
        List<Long> deptIdList = new ArrayList<>();
        List<SysDeptEntity> sysDeptEntityList = QueryChain.of(mapper).select(SysDeptEntityTableDef.SYS_DEPT_ENTITY.ID)
                .where(SysDeptEntityTableDef.SYS_DEPT_ENTITY.PIDS.like(id, Objects::nonNull)).list();
        if(CollectionUtils.isNotEmpty(sysDeptEntityList)){
            List<Long> collect = sysDeptEntityList.stream().map(SysDeptEntity::getId).collect(Collectors.toList());
            deptIdList.addAll(collect);
        }

        deptIdList.add(id);
        return deptIdList;
    }

    /**
     * 获取所有上级部门ID
     *
     * @param pid 上级ID
     */
    private String getPidList(Long pid) {
        //顶级部门，无上级部门
        if (Constant.DEPT_ROOT.equals(pid)) {
            return Constant.DEPT_ROOT + "";
        }

        //所有部门的id、pid列表
        List<SysDeptEntity> deptList = mapper.selectAll();

        //list转map
        Map<Long, SysDeptEntity> map = new HashMap<>(deptList.size());
        for (SysDeptEntity entity : deptList) {
            map.put(entity.getId(), entity);
        }

        //递归查询所有上级部门ID列表
        List<Long> pidList = new ArrayList<>();
        getPidTree(pid, map, pidList);

        return StringUtils.join(pidList, ",");
    }

    private void getPidTree(Long pid, Map<Long, SysDeptEntity> map, List<Long> pidList) {
        //顶级部门，无上级部门
        if (Constant.DEPT_ROOT.equals(pid)) {
            return;
        }

        //上级部门存在
        SysDeptEntity parent = map.get(pid);
        if (parent != null) {
            getPidTree(parent.getPid(), map, pidList);
        }

        pidList.add(pid);
    }
}
