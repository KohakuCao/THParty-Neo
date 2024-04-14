package com.nebulashrine.thparty.controller;

import com.nebulashrine.thparty.common.exceptions.EntityCannotFoundException;
import com.nebulashrine.thparty.common.response.Result;
import com.nebulashrine.thparty.common.utils.PartyUtils;
import com.nebulashrine.thparty.common.utils.UserUtils;
import com.nebulashrine.thparty.common.validGroup.CreateParty;
import com.nebulashrine.thparty.common.validGroup.DefaultParty;
import com.nebulashrine.thparty.entity.mysqlEntity.Party;
import com.nebulashrine.thparty.entity.mysqlEntity.User;
import com.nebulashrine.thparty.entity.vo.PartyVO;
import com.nebulashrine.thparty.service.serviceInterface.PartyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@Tag(name = "PartyController", description = "聚会相关Controller")
@RestController
@RequestMapping("/api/party")
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

    @Operation(summary = "getPartyInfo", description = "获取聚会信息")
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

    @Operation(summary = "createParty", description = "创建聚会")
    @Parameter(name = "PartyVO")
    @PostMapping("/createParty")
    public Result createParty(@Validated(CreateParty.class)PartyVO partyVO){
        User user = userUtils.getUser();
        Party tempParty = partyUtils.generateParty(user, partyVO);
        Party party = partyService.createParty(tempParty);
        return Result.succeed(party);
    }

    @Operation(summary = "deleteParty", description = "删除聚会")
    @Parameter(name = "PartyVO")
    @PostMapping("/deleteParty")
    public Result deleteParty(@Validated(DefaultParty.class) PartyVO partyVO){
        User user = userUtils.getUser();
        Party party = partyService.deleteParty(partyUtils.generateParty(user, partyVO));
        return Result.succeed(party);
    }

    @Operation(summary = "modifyParty", description = "编辑聚会")
    @Parameter(name = "PartyVO")
    @PostMapping("/modifyParty")
    public Result modifyParty(@Validated(DefaultParty.class) PartyVO partyVO){
        User user = userUtils.getUser();
        Party generatedParty = partyUtils.generateParty(user, partyVO);
        Party party = partyService.modifyParty(generatedParty);
        return Result.succeed(party);
    }

    @Operation(summary = "getAllParty", description = "获取全部聚会")
    @GetMapping("/")
    public Result getAllParty(){
        ArrayList<Party> parties = partyService.queryAllValidParty();
        return Result.succeed(parties);
    }
}
