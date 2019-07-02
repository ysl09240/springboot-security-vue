package com.sykean.hmhc.controller;

import com.sykean.hmhc.common.Constants;
import com.sykean.hmhc.common.ResponseCode;
import com.sykean.hmhc.common.RestResponse;
import com.sykean.hmhc.entity.User;
import com.sykean.hmhc.security.Token;
import com.sykean.hmhc.util.TokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RequestMapping("token")
@RestController
public class TokenController {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("refresh")
    public RestResponse refreshToken(@RequestHeader("token") String token) {
        ValueOperations<Object, Object> operations = redisTemplate.opsForValue();
        User user = (User) operations.get(Constants.Security.AUTH_TOKEN_KEY + token);
        if (user == null) {
            return RestResponse.error(ResponseCode.TOEKN_INVALID, null);
        }
        //如果已经刷新过了，返回刷新后的token
        String existToken = stringRedisTemplate.opsForValue().get(Constants.OLD_REFRESH_TOKEN_PREFIX + token);
        if (StringUtils.isNotBlank(existToken)) {
            Long expireTime = stringRedisTemplate.getExpire(Constants.USERNAME_TOKEN_PREFIX, TimeUnit.MILLISECONDS);
            return RestResponse.success(new Token(existToken, expireTime));
        }
        //保存新的token
        Token refreshToken = TokenUtil.generateToken();
        //保存token：username
        stringRedisTemplate.opsForValue().set(Constants.TOKEN_USERNAME_PREFIX + refreshToken.getAccessToken(), user.getUsername(), 1, TimeUnit.HOURS);
        //再保存username: token
        stringRedisTemplate.opsForValue().set(Constants.USERNAME_TOKEN_PREFIX + user.getUsername(), refreshToken.getAccessToken(), 1, TimeUnit.HOURS);
        //删除username: token
        stringRedisTemplate.delete(Constants.USERNAME_TOKEN_PREFIX + user.getUsername());
        //存入是否为刷新token TODO 时间不正确
        stringRedisTemplate.opsForValue().set(Constants.OLD_REFRESH_TOKEN_PREFIX + token, refreshToken.getAccessToken(), Constants.OLD_REFRESH_TOKEN_EXPIRE, TimeUnit.MINUTES);
        //认证对象放入redis
        redisTemplate.opsForValue().set(Constants.Security.AUTH_TOKEN_KEY + refreshToken.getAccessToken(), user, 1, TimeUnit.HOURS);
        return RestResponse.success(refreshToken);
    }
}
