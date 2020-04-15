package com.frozen.member.service;

import com.alibaba.fastjson.JSONObject;
import com.frozen.FrozenCommException;
import com.frozen.member.api.MemberCommService;
import com.frozen.member.bean.UserEntity;
import com.frozen.member.mapper.UserMapper;
import com.frozen.member.utils.MemberMD5Util;
import com.frozen.member.utils.MemberRedisUtil;
import com.frozen.message.bean.MailMessageEntity;
import com.frozen.message.constants.ExposeMsgConstants;
import com.frozen.response.ResponseDataEntity;
import com.frozen.service.RedisService;
import com.frozen.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import java.util.Map;

/**
 * <program> shopparent </program>
 * <description> Member通用操作服务实现 </description>
 *
 * @author : lw
 * @date : 2020-03-26 20:52
 **/
@RestController
@Slf4j
public class MemberCommServiceImpl implements MemberCommService {
    @Autowired
    private RedisService redisService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    private MemberLoginService memberLoginService;

    @Override
    public ResponseDataEntity<Object> register(@RequestBody UserEntity userEntity) {
        try {
            checkRegisterUser(userEntity);
        } catch (FrozenCommException e) {
            return ResponseUtil.getResponseError(e.getMessage(), null);
        }
        String salt = MemberMD5Util.getSalt();
        String encryptPassword = MemberMD5Util.encryptPassword(userEntity.getPassword(), salt);
        userEntity.setPassword(encryptPassword);
        userEntity.setSalt(salt);
        userMapper.insertSelective(userEntity);
        String json = JSONObject.toJSONString(new MailMessageEntity(ExposeMsgConstants.MAIL_INTERFACE_TYPE, userEntity.getUsername(), userEntity.getEmail()));
        //向消息队列推送注册信息
        jmsMessagingTemplate.convertAndSend(ExposeMsgConstants.REGISTER_QUEUE, json);
        log.info("推送成功");
        return ResponseUtil.getResponseSuccess("恭喜,注册成功!!", null);
    }

    /**
     * <description> 校验用户注册信息以及用户名和邮箱是否重复 </description>
     *
     * @param userEntity :  用户注册实体类
     * @return : void
     * @author : lw
     * @date : 2020/3/27 11:08
     */
    private void checkRegisterUser(UserEntity userEntity) throws FrozenCommException {
        //校验用户名和密码
        checkUsernameAndPassword(userEntity);
        //校验邮箱
        if (StringUtils.isBlank(userEntity.getEmail())) {
            throw new FrozenCommException("请输入邮箱");
        }
        //校验用户名和邮箱是否重复
        Example example = new Example(UserEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username", userEntity.getUsername());
        criteria.orEqualTo("email", userEntity.getEmail());
        int count = userMapper.selectCountByExample(example);
        if (count > 0) {
            throw new FrozenCommException("用户名或邮箱已被使用");
        }
    }

    /**
     * <description> 校验用户名和密码 </description>
     *
     * @param userEntity :
     * @return : void
     * @author : lw
     * @date : 2020/3/27 11:17
     */
    private void checkUsernameAndPassword(UserEntity userEntity) throws FrozenCommException {
        if (StringUtils.isBlank(userEntity.getUsername())) {
            throw new FrozenCommException("请输入用户名");
        }
        if (StringUtils.isBlank(userEntity.getPassword())) {
            throw new FrozenCommException("请输入密码");
        }
    }

    @Override
    public ResponseDataEntity<Map<String, String>> login(@RequestBody UserEntity userEntity) {
        //1.校验参数
        try {
            checkUsernameAndPassword(userEntity);
        } catch (FrozenCommException e) {
            return ResponseUtil.getResponseError(e.getMessage(), null);
        }
        //2.根据用户名查找
        Example example = new Example(UserEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username", userEntity.getUsername());
        UserEntity selectUser = userMapper.selectOneByExample(example);
        //3.判断用户是否存在
        if (selectUser == null) {
            return ResponseUtil.getResponse(HttpStatus.NOT_FOUND.value(), "该用户名不存在!", null);
        }
        String encryptPassword = MemberMD5Util.encryptPassword(userEntity.getPassword(), selectUser.getSalt());
        //判断账号密码是否一致
        if (!encryptPassword.equals(selectUser.getPassword())) {
            return ResponseUtil.getResponse(HttpStatus.UNAUTHORIZED.value(), "用户名或密码错误!", null);
        }
        return memberLoginService.loginByUserId(selectUser.getId());
    }

    @Override
    public ResponseDataEntity<UserEntity> queryLoginUserByToken(String token) {
        if (StringUtils.isBlank(token)) {
            return ResponseUtil.getResponse(HttpStatus.UNAUTHORIZED.value(), "无登录token,请去登录", null);
        }
        String id = redisService.getString(MemberRedisUtil.getRedisKey(token));
        if (StringUtils.isBlank(id)) {
            return ResponseUtil.getResponse(HttpStatus.UNAUTHORIZED.value(), "无效token或token已过期,请去登录", null);
        }
        log.info(id);
        UserEntity userEntity = userMapper.selectByPrimaryKey(Long.valueOf(id));
        if (userEntity == null) {
            return ResponseUtil.getResponseError("用户不存在", null);
        }
        userEntity.setPassword(null);
        return ResponseUtil.getResponseSuccess(userEntity);
    }


}
