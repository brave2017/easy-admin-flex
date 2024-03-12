

package com.easy.common.aspect;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.qiniu.util.StringUtils;
import com.easy.common.annotation.DataFilter;
import com.easy.common.constant.Constant;
import com.easy.common.exception.ErrorCode;
import com.easy.common.exception.ServiceException;
import com.easy.common.interceptor.DataScope;
import com.easy.modules.sys.enums.SuperAdminEnum;
import com.easy.modules.security.user.SecurityUser;
import com.easy.modules.security.user.UserDetail;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 数据过滤，切面处理类
 *
 * @author Maw
 */
@Aspect
@Component
public class DataFilterAspect {

    @Pointcut("@annotation(com.easy.common.annotation.DataFilter)")
    public void dataFilterCut() {

    }

    @Before("dataFilterCut()")
    public void dataFilter(JoinPoint point) {
        Object params = point.getArgs()[0];
        if (params != null && params instanceof Map) {
            UserDetail user = SecurityUser.getUser();

            //如果是超级管理员，则不进行数据过滤
            if (Objects.equals(user.getSuperAdmin(), SuperAdminEnum.YES.getValue())) {
                return;
            }

            try {
                //否则进行数据过滤
                Map map = (Map) params;
                String sqlFilter = getSqlFilter(user, point);
                map.put(Constant.SQL_FILTER, new DataScope(sqlFilter));
            } catch (Exception e) {

            }

            return;
        }

        throw new ServiceException(ErrorCode.DATA_SCOPE_PARAMS_ERROR);
    }

    /**
     * 获取数据过滤的SQL
     */
    private String getSqlFilter(UserDetail user, JoinPoint point) throws Exception {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = point.getTarget().getClass().getDeclaredMethod(signature.getName(), signature.getParameterTypes());
        DataFilter dataFilter = method.getAnnotation(DataFilter.class);

        //获取表的别名
        String tableAlias = dataFilter.tableAlias();
        if (StrUtil.isNotBlank(tableAlias)) {
            tableAlias += ".";
        }

        StringBuilder sqlFilter = new StringBuilder();
        sqlFilter.append(" (");

        //部门ID列表
        List<Long> deptIdList = user.getDeptIdList();
        if (CollUtil.isNotEmpty(deptIdList)) {
            sqlFilter.append(tableAlias).append(dataFilter.deptId());

            sqlFilter.append(" in(").append(StringUtils.join(deptIdList, ",")).append(")");
        }

        //查询本人数据
        if (CollUtil.isNotEmpty(deptIdList)) {
            sqlFilter.append(" or ");
        }
        sqlFilter.append(tableAlias).append(dataFilter.userId()).append("=").append(user.getId());

        sqlFilter.append(")");

        return sqlFilter.toString();
    }
}
