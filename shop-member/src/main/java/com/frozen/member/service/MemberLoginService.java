package com.frozen.member.service;

import com.frozen.member.common.MemberConstants;
import com.frozen.member.utils.MemberRedisUtil;
import com.frozen.member.utils.MemberTokenUtil;
import com.frozen.response.ResponseDataEntity;
import com.frozen.service.RedisService;
import com.frozen.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <program> shop-parent </program>
 * <description>  </description>
 *
 * @author : lw
 * @date : 2020-04-13 11:14
 **/
@Service
public class MemberLoginService {

    @Autowired
    private RedisService redisService;

    /**
     * <description>  </description>
     *
     * @param userId :
     * @return : com.frozen.response.ResponseDataEntity<com.frozen.member.bean.UserEntity>
     * @author : lw
     * @date : 2020/4/13 11:07
     */
    public ResponseDataEntity<Map<String, String>> loginByUserId(Long userId) {
        if (userId == null) {
            return ResponseUtil.getResponseError("登录失败", null);
        }
        //获取登录token
        String token = MemberTokenUtil.getToken();
        //放入redis
        redisService.setString(MemberRedisUtil.getRedisKey(token), userId.toString(), MemberConstants.LOGIN_EXPIRE_TIME, TimeUnit.DAYS);
        //返回
        Map<String, String> data = new HashMap<>(1);
        data.put("token", token);
        return ResponseUtil.getResponseSuccess("登录成功", data);
    }
}
