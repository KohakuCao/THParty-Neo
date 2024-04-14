package com.nebulashrine.thparty.service.serviceInterface.games;

import com.nebulashrine.thparty.entity.dto.AnnoyingUFODTO;

public interface AnnoyingUFO {

    AnnoyingUFODTO getRandom() throws Exception;

    String getRandomWord(AnnoyingUFODTO annoyingUFODTO);
}
