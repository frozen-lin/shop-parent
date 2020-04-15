package com.frozen.member.service;

import com.frozen.member.api.MemberGitHubService;
import com.frozen.member.bean.UserEntity;
import com.frozen.member.mapper.UserMapper;
import com.frozen.response.ResponseDataEntity;
import com.frozen.utils.ResponseUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import java.util.Map;

/**
 * <program> shop-parent </program>
 * <description> 会员GitHub服务实现类 </description>
 *
 * @author : lw
 * @date : 2020-04-13 11:02
 **/
@RestController
public class MemberGitHubServiceImpl implements MemberGitHubService {

    @Autowired
    private MemberLoginService memberLoginService;
    @Autowired
    private UserMapper userMapper;

    @Override
    public ResponseDataEntity<Map<String, String>> gitLogin(@PathVariable("gitUserId")String gitUserId) {
        ResponseDataEntity<UserEntity> userEntityResponse = queryUserByGitUserId(gitUserId);
        if (!ResponseUtil.checkResponseOk(userEntityResponse)) {
            return ResponseUtil.getResponseError("登录失败,gitUserId未关联用户", null);
        }
        return memberLoginService.loginByUserId(userEntityResponse.getData().getId());
    }

    @Override
    public ResponseDataEntity<UserEntity> queryUserByGitUserId(@PathVariable("gitUserId") String gitUserId) {
        if (StringUtils.isBlank(gitUserId)) {
            return ResponseUtil.getResponseError("请传入gitUserId", null);
        }
        Example example = new Example(UserEntity.class);
        final Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("githubUserId", gitUserId);
        UserEntity selectUser = userMapper.selectOneByExample(example);
        if (selectUser == null) {
            return ResponseUtil.getResponseError("gitHubUserId未关联用户", null);
        }
        selectUser.setPassword("");
        return ResponseUtil.getResponseSuccess(selectUser);
    }

    @Override
    public ResponseDataEntity<Object> bindGitUserId(@RequestBody UserEntity userEntity) {
        if (userEntity==null||userEntity.getUsername()==null||StringUtils.isBlank(userEntity.getGithubUserId())){
            return ResponseUtil.getResponseError("传入参数有误",null);
        }
        UserEntity updateEntity = new UserEntity();
        updateEntity.setGithubUserId(userEntity.getGithubUserId());
        updateEntity.setId(userEntity.getId());
        Example example = new Example(UserEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username", userEntity.getUsername());
        userMapper.updateByExampleSelective(updateEntity,example);
        return ResponseUtil.getResponseSuccess();
    }
}
