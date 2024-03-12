package com.easy.common.exception;

import cn.hutool.core.map.MapUtil;
import com.easy.common.utils.HttpContextUtils;
import com.easy.common.utils.IpUtils;
import com.easy.common.utils.Result;
import com.easy.modules.log.entity.SysLogErrorEntity;
import com.easy.modules.log.service.SysLogErrorService;
import com.easy.common.utils.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


/**
 * 异常处理器
 *
 * @author Maw
 * @since 1.0.0
 */
@Slf4j
@RestControllerAdvice
@AllArgsConstructor
public class ServiceExceptionHandler {
    private final SysLogErrorService sysLogErrorService;

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(ServiceException.class)
    public Result handleRenException(ServiceException ex) {
        Result result = new Result();
        result.error(ex.getCode(), ex.getMsg());

        return result;
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public Result handleDuplicateKeyException(DuplicateKeyException ex) {
        return Result.error(ErrorCode.DB_RECORD_EXISTS);
    }

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception ex) {
        log.error(ex.getMessage(), ex);

        saveLog(ex);

        return new Result().error();
    }

    /**
     * 保存异常日志
     */
    private void saveLog(Exception ex) {
        SysLogErrorEntity log = new SysLogErrorEntity();

        //请求相关信息
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        log.setIp(IpUtils.getIpAddr(request));
        log.setUserAgent(request.getHeader(HttpHeaders.USER_AGENT));
        log.setRequestUri(request.getRequestURI());
        log.setRequestMethod(request.getMethod());
        Map<String, String> params = HttpContextUtils.getParameterMap(request);
        if (MapUtil.isNotEmpty(params)) {
            log.setRequestParams(JsonUtils.toJsonString(params));
        }

        //异常信息
        log.setErrorInfo(ExceptionUtils.getErrorStackTrace(ex));

        //保存
        sysLogErrorService.save(log);
    }
}
