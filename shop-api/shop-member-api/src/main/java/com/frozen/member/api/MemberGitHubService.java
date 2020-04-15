package com.frozen.member.api;

import com.frozen.member.bean.UserEntity;
import com.frozen.response.ResponseDataEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <program> shop-parent </program>
 * <description> 会员github 服务接口 </description>
 *
 * @author : lw
 * @date : 2020-04-13 10:49
 **/
@RequestMapping("/api/member/git")
public interface MemberGitHubService {
    /**
     * <description> 根据git用户Id进行登录 </description>
     *
     * @param gitUserId :git的用户Id
     * @return : com.frozen.response.ResponseDataEntity<java.lang.String> 若成功返回登录token字符串
     * @author : lw
     * @date : 2020/4/13 10:58
     */
    @PostMapping("/gitLogin/{gitUserId}")
    ResponseDataEntity<Map<String, String>> gitLogin(@PathVariable("gitUserId") String gitUserId);

    /**
     * <description> 根据git用户Id查询用户信息 </description>
     *
     * @param gitUserId :  git的用户Id
     * @return : com.frozen.response.ResponseDataEntity<com.frozen.member.bean.UserEntity>
     * @author : lw
     * @date : 2020/4/13 10:59
     */
    @GetMapping("/queryUserByGitUserId/{gitUserId}")
    ResponseDataEntity<UserEntity> queryUserByGitUserId(@PathVariable("gitUserId") String gitUserId);

    /**
     * <description> 将传入的实体对象的githubUserId和用户username进行绑定 </description>
     *
     * @param userEntity : 包含githubUserId和用户username
     * @return : com.frozen.response.ResponseDataEntity<java.lang.Object>
     * @author : lw
     * @date : 2020/4/13 13:27
     */
    @PostMapping("/bindGitUserId")
    ResponseDataEntity<Object> bindGitUserId(@RequestBody UserEntity userEntity);
}
