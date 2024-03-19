package com.nebulashrine.thparty.controller;

import com.nebulashrine.thparty.common.response.Result;
import com.nebulashrine.thparty.common.response.StatusCode;
import com.nebulashrine.thparty.entity.mysqlEntity.User;
import com.nebulashrine.thparty.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Collections;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserAuthController {

    private UserService userService;


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

    @PostMapping("/registerViaTHP")
    public Result registerViaTHP(@AuthenticationPrincipal OAuth2User principal){
        if (ObjectUtils.isEmpty(principal)){
            return Result.error(StatusCode.INVALID_PARAMETERS.getStatusCode(), StatusCode.INVALID_PARAMETERS.getResultMessage());
        }
        Map<String, Object> attributes = principal.getAttributes();
        User user = userService.createUser((String) attributes.get("sub"), (String) attributes.get("jti"));

        SecurityContext emptyContext = SecurityContextHolder.createEmptyContext();
        new
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
