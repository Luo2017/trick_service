package com.trick.trick_class.controller;

import com.trick.trick_class.model.entity.User;
import com.trick.trick_class.model.request.LoginRequest;
import com.trick.trick_class.service.UserService;
import com.trick.trick_class.utils.JWTUtils;
import com.trick.trick_class.utils.JsonData;


import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("api/v1/pri/user")
public class UserContoller {



    @Autowired
    private UserService userService;

    /**
     * 注册接口
     * @param userInfo
     * @return
     */
    @PostMapping("register")
    public JsonData register(@RequestBody Map<String,String> userInfo ){

        int rows = userService.save(userInfo);

        return rows == 1 ? JsonData.buildSuccess(): JsonData.buildError("注册失败，请重试");

    }

    /**
     * 登陆接口
     * @param loginRequest
     * @return
     */
    @PostMapping("login")
    public JsonData login(@RequestBody LoginRequest loginRequest) {
        String token = userService.findByPhoneAndPwd(loginRequest.getPhone(), loginRequest.getPwd());
        //只有用户密码匹配时才返回token
        return token == null ? JsonData.buildError("登陆失败，账号密码错误") : JsonData.buildSuccess(token);
    }


    /**
     * 测试生成令牌和校验令牌的功能，仅仅用于测试，后期可以删除
     */
    @PostMapping("jump")
    public void jump() {
        String token = userService.findByPhoneAndPwd("111", "12345");

        if(token == null) {
            System.out.println("登陆失败，账号密码错误");
        } else {
            System.out.println("token = " + token);
        }

        Claims claims = JWTUtils.checkJWT(token);
        System.out.println(claims.get("name"));
    }

    /**
     * 根据用户id查询用户信息，访问时先经过拦截器通过后，会有user_id属性
     * @param request
     * @return
     */
    @GetMapping("find_by_token")
    public JsonData findUserInfoByToken(HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute("user_id");

        if(userId == null) {
            return JsonData.buildError("查询失败");
        }

        User user = userService.findByUserId(userId);
        return JsonData.buildSuccess(user);
    }
}
