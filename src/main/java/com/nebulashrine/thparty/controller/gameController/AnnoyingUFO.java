package com.nebulashrine.thparty.controller.gameController;

import com.nebulashrine.thparty.common.response.Result;
import com.nebulashrine.thparty.common.response.StatusCode;
import com.nebulashrine.thparty.common.utils.UserUtils;
import com.nebulashrine.thparty.entity.dto.AnnoyingUFODTO;
import com.nebulashrine.thparty.entity.mysqlEntity.User;
import com.nebulashrine.thparty.service.serviceImpl.games.AnnoyingUFOImpl;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/games/annoyingUFO")
public class AnnoyingUFO {

    private final AnnoyingUFOImpl annoyingUFO;

    private final UserUtils userUtils;

    private final ServletContext servletContext;

    @Autowired
    public AnnoyingUFO(AnnoyingUFOImpl annoyingUFO, UserUtils userUtils, ServletContext servletContext) {
        this.annoyingUFO = annoyingUFO;
        this.userUtils = userUtils;
        this.servletContext = servletContext;
    }

    @GetMapping("/getRandomWord")
    public Result getRandomWord() throws Exception {
        User user = userUtils.getUser();
        if (!"ADMIN".equals(user.getAuthority())){
            return Result.error(StatusCode.USER_AUTH_ERROR.getStatusCode(), "你没有权限进行此操作");
        }
        AnnoyingUFODTO random = null;
        if (servletContext.getAttribute("random") == null){
            random = annoyingUFO.getRandom();
            servletContext.setAttribute("random", random);
        }else {
            random = (AnnoyingUFODTO) servletContext.getAttribute("random");
        }
        String randomWord = annoyingUFO.getRandomWord(random);
        return Result.succeed(randomWord);
    }

    @PostMapping("/getRandom")
    public Result getRandom() throws Exception {
        User user = userUtils.getUser();
        if (!"ADMIN".equals(user.getAuthority())){
            return new Result(StatusCode.USER_AUTH_ERROR.getStatusCode(), "你无权进行此操作");
        }
        AnnoyingUFODTO random = annoyingUFO.getRandom();
        servletContext.setAttribute("random", random);

        return Result.succeed(random);
    }
}
