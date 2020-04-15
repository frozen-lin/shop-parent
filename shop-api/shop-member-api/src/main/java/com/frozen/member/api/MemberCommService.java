package com.frozen.member.api;

import com.frozen.member.bean.UserEntity;
import com.frozen.response.ResponseDataEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * <program> shopparent </program>
 * <description> Member通用操作服务接口 注册登录等 </description>
 *
 * @author : lw
 * @date : 2020-03-26 20:48
 **/
@RequestMapping("/api/member")
public interface MemberCommService {
    /**
     * <description> 注册 </description>
     *
     * @param userEntity :包含用户注册信息
     * @return :
     * @author : lw
     * @date : 2020/3/26 20:51
     */
    @RequestMapping("/register")
    ResponseDataEntity<Object> register(@RequestBody UserEntity userEntity);

    /**
     * <description> 登录 </description>
     *
     * @param userEntity : 包含username,password用于登录
     * @return :
     * @author : lw
     * @date : 2020/3/26 20:51
     */
    @RequestMapping("/login")
    ResponseDataEntity<Map<String, String>> login(@RequestBody UserEntity userEntity);

  /**
   * <description> 根据登录token查询用户信息 </description>
   * @param token :
   * @return :
   * @author : lw
   * @date : 2020/3/27 16:50
   */
    @RequestMapping("/queryLoginUserByToken")
    ResponseDataEntity<UserEntity> queryLoginUserByToken(@RequestParam("token") String token);
}
