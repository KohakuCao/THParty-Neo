package com.nebulashrine.thparty.controller;


import com.nebulashrine.thparty.common.response.Result;
import com.nebulashrine.thparty.common.response.StatusCode;
import com.nebulashrine.thparty.common.utils.UserUtils;
import com.nebulashrine.thparty.entity.mysqlEntity.User;
import com.nebulashrine.thparty.service.serviceInterface.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Collections;
import java.util.Map;

@Tag(name = "UserAuthController", description = "用户权限管理")
@Controller
@RequestMapping("/user")
public class UserAuthController {

    private final UserService userService;

    private final UserUtils userUtils;


    @Autowired
    public UserAuthController(UserService userService, UserUtils userUtils) {
        this.userService = userService;
        this.userUtils = userUtils;
    }

    /*
        临时方法，为了测试oauth2接入
    */
    @GetMapping("/loginWithTHPassport")
    public RedirectView loginWithTHPassport(){
        return new RedirectView("/oauth2/authorization/thpassport");
    }

    /**
     * @param principal
     * @param response
     * @return
     */
    @Operation(summary = "loginViaTHP", description = "通过THP登录")
    @ResponseBody
    @RequestMapping("/loginViaTHP")
    public Result loginViaTHP(@AuthenticationPrincipal OAuth2User principal, HttpServletResponse response){
        if (ObjectUtils.isEmpty(principal)){
            return Result.error(StatusCode.INVALID_PARAMETERS.getStatusCode(), StatusCode.INVALID_PARAMETERS.getResultMessage());
        }
        Map<String, Object> attributes = principal.getAttributes();
        User user = userService.queryUser((String) attributes.get("sub"));
        if (user != null){
            return Result.succeed(user);
        }
        SecurityContextHolder.setContext(SecurityContextHolder.createEmptyContext());
        user = userService.createUser((String) attributes.get("sub"), (String) attributes.get("jti"));
        user = userService.addUser(user);
        return Result.succeed(user);

    }


    @GetMapping("/data")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        principal.getAttributes().forEach((k, v)->{
            System.out.println(k + " -> " + v);
        });
        System.out.println(principal.getName());
        return Collections.singletonMap("name", principal.getAttribute("sub"));
    }

    @Operation(summary = "userInfo", description = "获取用户信息")
    @Parameter(name = "username", description = "用户名(为空则为自身信息)")
    @ResponseBody
    @GetMapping("/info/{username}")
    public Result userInfo(@PathVariable("username") String username){
        User user = null;
        if (null == username || username.isEmpty()){
            user = userUtils.getUser();
            if (user == null){
                return Result.error(StatusCode.USER_NOT_LOGIN.getStatusCode(), StatusCode.USER_NOT_LOGIN.getResultMessage());
            }
        }else {
            user = userService.queryUser(username);
            if (user == null){
                return Result.error(StatusCode.NOT_FOUND.getStatusCode(), "该用户不存在");
            }
        }
        user.setPassword("");
        user.setAuthority("");
        user.setId(2147483647);
        return Result.succeed(user,"查询成功");
    }

}
