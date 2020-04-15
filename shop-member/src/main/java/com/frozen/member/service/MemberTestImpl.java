package com.frozen.member.service;

import com.frozen.member.bean.TestMember;
import com.frozen.member.utils.MemberRedisUtil;
import com.frozen.response.ResponseDataEntity;
import com.frozen.service.RedisService;
import com.frozen.utils.ResponseUtil;
import com.frozen.member.api.MemberTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;


/**
 * <program> shopparent </program>
 * <description> Member测试接口实现类 </description>
 *
 * @author : lw
 * @date : 2020-03-25 14:46
 **/
@RestController
public class MemberTestImpl implements MemberTestService {


    @Autowired
    private RedisService redisService;

    @Override
    public ResponseDataEntity<TestMember> testMember(Long no, String name) {
        return ResponseUtil.getResponseSuccess(new TestMember(no,name));
    }

    @Override
    public ResponseDataEntity<Object> testRedisSetMember(Long no, String name) {
        redisService.setJsonObject(MemberRedisUtil.getRedisKey(no.toString()),new TestMember(no,name));
        return ResponseUtil.getResponseSuccess();
    }

    @Override
    public ResponseDataEntity<TestMember> testRedisGetMember(Long no) {
        return ResponseUtil.getResponseSuccess(redisService.getJsonObject(MemberRedisUtil.getRedisKey(no.toString()), TestMember.class));
    }
}
