package com.frozen.pc.web.service;

import com.frozen.member.api.MemberGitHubService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * <program> shop-parent </program>
 * <description>  </description>
 *
 * @author : lw
 * @date : 2020-04-13 11:27
 **/
@FeignClient("member-service")
public interface MemberGitHubFeignService extends MemberGitHubService {

}
