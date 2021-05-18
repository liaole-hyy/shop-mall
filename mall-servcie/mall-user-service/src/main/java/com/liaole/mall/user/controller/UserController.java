package com.liaole.mall.user.controller;

import com.liaole.mall.config.IPUtils;
import com.liaole.mall.util.JwtToken;
import com.liaole.mall.util.MD5;
import com.liaole.mall.util.RespResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private String username = "liaole";
    private String pwd = "123456";

    /**
     *  模拟登陆
     * @param username
     * @param pwd
     * @param request
     * @return
     */
    @PostMapping(value = "/login")
    public RespResult login(@RequestParam(value = "username") String username,
                            @RequestParam(value = "pwd") String pwd, HttpServletRequest request) throws Exception{
        if(this.username.equals(username) && this.pwd.equals(pwd)){
            //登陆成功
            Map<String,Object> body = new HashMap<String,Object>();
            body.put("username",username);
            body.put("pwd",pwd);
            body.put("ip", MD5.md5(IPUtils.getIpAddr(request)));
            String token = JwtToken.createToken(body);
            return RespResult.ok(token);
        }
        return RespResult.error("用户名或者密码错误");
    }
}
