package com.nebulashrine.thparty.controller;

import com.nebulashrine.thparty.common.exceptions.EntityCannotFoundException;
import com.nebulashrine.thparty.common.response.Result;
import com.nebulashrine.thparty.common.utils.PartyUtils;
import com.nebulashrine.thparty.common.utils.UserUtils;
import com.nebulashrine.thparty.common.validGroup.CreateParty;
import com.nebulashrine.thparty.entity.mysqlEntity.Party;
import com.nebulashrine.thparty.entity.mysqlEntity.User;
import com.nebulashrine.thparty.entity.vo.PartyVO;
import com.nebulashrine.thparty.service.serviceInterface.PartyService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/party")
public class PartyController {

    private final PartyService partyService;

    private final PartyUtils partyUtils;

    private final UserUtils userUtils;

    @Autowired
    public PartyController(PartyUtils partyUtils, PartyService partyService, UserUtils userUtils) {
        this.partyService = partyService;
        this.partyUtils = partyUtils;
        this.userUtils = userUtils;
    }

    @GetMapping("/info")
    public Result getParty(HttpServletRequest request){
        String[] url = request.getRequestURL().toString().split(".");
        String partyPath = url[0];
        Party party = partyService.queryParty(partyPath);
        if (party == null){
            throw new EntityCannotFoundException("can't find this party");
        }
         return Result.succeed(party);
    }

    @PostMapping("/createParty")
    public Result createParty(@Validated(CreateParty.class)PartyVO partyVO){
        User user = userUtils.getUser();
        Party tempParty = partyUtils.generateParty(user, partyVO);
        Party party = partyService.createParty(tempParty);
        return Result.succeed(party);
    }
}
