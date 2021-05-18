package com.liaole.mall.user.fegin;

import com.liaole.mall.util.RespResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@FeignClient(value = "mall-user")
public interface UserFegin {

    @PostMapping(value = "/user/login")
    public RespResult login(@RequestParam(value = "username") String username,
                            @RequestParam(value = "pwd") String pwd, HttpServletRequest request) throws Exception;
}
