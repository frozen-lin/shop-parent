package com.frozen.pc.web.service;

import com.frozen.member.api.MemberCommService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author LIN
 */
@FeignClient("member-service")
public interface MemberCommFeignService extends MemberCommService {
}
