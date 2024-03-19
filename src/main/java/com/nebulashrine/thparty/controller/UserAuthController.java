package com.nebulashrine.thparty.controller;

import com.nebulashrine.thparty.common.exceptions.JwtAuthException;
import com.nebulashrine.thparty.common.response.Result;
import com.nebulashrine.thparty.common.response.StatusCode;
import com.nebulashrine.thparty.entity.mysqlEntity.User;
import com.nebulashrine.thparty.service.AuthService;
import com.nebulashrine.thparty.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Collections;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserAuthController {

    private final UserService userService;


    @Autowired
    public UserAuthController(UserService userService) {
        this.userService = userService;
    }

    /*
        临时方法，为了测试oauth2接入
    */
    @GetMapping("/loginWithTHPassport")
    public RedirectView loginWithTHPassport(){
        return new RedirectView("/oauth2/authorization/thpassport");
    }

    /**
     * TODO: 没有必要创建新的SecurityContext,直接使用oauth2token认证，token指向一个对象
     * @param principal
     * @param response
     * @return
     * @throws JwtAuthException
     */
    @ResponseBody
    @RequestMapping("/loginViaTHP")
    public Result loginViaTHP(@AuthenticationPrincipal OAuth2User principal, HttpServletResponse response) throws JwtAuthException {
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

    @GetMapping("/info")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        principal.getAttributes().forEach((k, v)->{
            System.out.println(k + " -> " + v);
        });
        System.out.println(principal.getName());
        return Collections.singletonMap("name", principal.getAttribute("sub"));
    }

}
