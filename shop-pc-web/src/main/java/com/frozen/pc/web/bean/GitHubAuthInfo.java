package com.frozen.pc.web.bean;

import lombok.Data;

/**
 * <program> shop-parent </program>
 * <description> gitHub授权信息 </description>
 *
 * @author : lw
 * @date : 2020-04-12 22:13
 **/
@Data
public class GitHubAuthInfo {
    private String token_type;
    private String scope;
    private String  access_token;
}
