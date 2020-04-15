package com.frozen.member.api;

import com.frozen.member.bean.TestMember;
import com.frozen.response.ResponseDataEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <description> 会员接口测试类 </description>
 * @author : lw
 * @date : 2020/3/25 13:58
 */
@RequestMapping("/api/member/test")
public interface MemberTestService {
    /**
     * <description> 测试接口 </description>
     * @param no :会员号
     * @param name : 会员姓名
     * @return :  ResponseDataEntity<Member>
     * @author : lw
     * @date : 2020/3/25 14:32
     */
    @RequestMapping("/getMember")
    ResponseDataEntity<TestMember> testMember(@RequestParam("no") Long no, @RequestParam("name") String name);

    /**
     * <description> 测试redis存储接口 </description>
     * @param no : 会员号
     * @param name :会员姓名
     * @return :ResponseDataEntity<Object>
     * @author : lw
     * @date : 2020/3/25 20:44
     */
    @RequestMapping("/setRedisMember")
    ResponseDataEntity<Object> testRedisSetMember(@RequestParam("no") Long no, @RequestParam("name") String name);

    /**
     * <description> 测试redis查询接口 </description>
     * @param no : 会员号
     * @return :ResponseDataEntity<Member>
     * @author : lw
     * @date : 2020/3/25 20:44
     */
    @RequestMapping("/getRedisMember")
    ResponseDataEntity<TestMember> testRedisGetMember(@RequestParam("no") Long no);
}
