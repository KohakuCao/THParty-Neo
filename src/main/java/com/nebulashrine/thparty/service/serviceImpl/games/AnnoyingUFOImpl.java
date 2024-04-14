package com.nebulashrine.thparty.service.serviceImpl.games;

import com.nebulashrine.thparty.entity.dto.AnnoyingUFODTO;
import com.nebulashrine.thparty.repository.games.AnnoyingUFORepo;
import com.nebulashrine.thparty.service.serviceInterface.games.AnnoyingUFO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AnnoyingUFOImpl implements AnnoyingUFO {


    private AnnoyingUFORepo annoyingUFORepo;

    @Autowired
    public AnnoyingUFOImpl(AnnoyingUFORepo annoyingUFORepo) {
        this.annoyingUFORepo = annoyingUFORepo;
    }

    @Override
    public AnnoyingUFODTO getRandom() throws Exception {
        com.nebulashrine.thparty.entity.mysqlEntity.games.AnnoyingUFO random = annoyingUFORepo.getRandom();
        AnnoyingUFODTO dto = new AnnoyingUFODTO(random);
        return  dto;
    }

    @Override
    public String getRandomWord(AnnoyingUFODTO annoyingUFODTO) {
        ArrayList<String> words = annoyingUFODTO.getWords();
        int index = (int) (Math.random() * words.size());
        return words.get(index);
    }
}
 