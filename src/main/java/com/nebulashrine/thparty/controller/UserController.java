package com.nebulashrine.thparty.controller;

import com.nebulashrine.thparty.common.response.Result;
import com.nebulashrine.thparty.common.response.StatusCode;
import com.nebulashrine.thparty.common.utils.UserUtils;
import com.nebulashrine.thparty.common.validGroup.DefaultParty;
import com.nebulashrine.thparty.entity.mysqlEntity.User;
import com.nebulashrine.thparty.entity.vo.PartyVO;
import com.nebulashrine.thparty.entity.vo.UserVO;
import com.nebulashrine.thparty.service.serviceInterface.PartyService;
import com.nebulashrine.thparty.service.serviceInterface.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final PartyService partyService;

    private final UserUtils userUtils;

    @Autowired
    public UserController(UserService userService, PartyService partyService, UserUtils userUtils) {
        this.userService = userService;
        this.partyService = partyService;
        this.userUtils = userUtils;
    }

    @PostMapping("/changeProfile")
    public Result changeProfile(@Valid UserVO userVO) throws Exception{
        User user = userUtils.getUser();
        if (user == null){
            return Result.error(StatusCode.USER_NOT_LOGIN.getStatusCode(), StatusCode.USER_NOT_LOGIN.getResultMessage());
        }
        User newUser = userUtils.generateUser(userVO);
        user = userService.modifyUserDetails(user, newUser);
        return Result.succeed(user);
    }

    @PostMapping("/joinParty")
    @ResponseBody
    public Result joinParty(@Validated(DefaultParty.class)PartyVO partyVO){
        User user = userService.joinParty(userUtils.getUser().getUsername(), partyVO.getId());
        return Result.succeed(user);
    }

    @PostMapping("/leaveParty")
    @ResponseBody
    public Result leaveParty(@Validated(DefaultParty.class)PartyVO partyVO){
        User user = userService.leaveParty(userUtils.getUser().getUsername(), partyVO.getId());
        return Result.succeed(user);
    }
}
