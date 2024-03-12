

package com.easy;

import cn.hutool.core.util.StrUtil;
import com.easy.common.redis.RedisUtils;
import com.easy.modules.sys.entity.SysUserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class RedisTest {
    @Resource
    private RedisUtils redisUtils;

    @Test
    public void contextLoads() {
        SysUserEntity user = new SysUserEntity();
        user.setEmail("123456@qq.com");
        redisUtils.set("user", user);
        System.out.println(StrUtil.toString(redisUtils.get("user")));
    }

}
