package com.nebulashrine.thparty.entity.vo;

import com.nebulashrine.thparty.common.validGroup.CreateParty;
import com.nebulashrine.thparty.common.validGroup.DefaultParty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PartyVO {
    @NotNull(groups = {DefaultParty.class, CreateParty.class})
    private int id;

    @NotNull(groups = CreateParty.class)
    private String title;

    private String subTitle;

    private String information;

    @NotNull(groups = CreateParty.class)
    private Date startTime;

    @NotNull(groups = CreateParty.class)
    private Date endTime;

    private String domain;

    @NotNull(groups = CreateParty.class)
    private String[] leadersName;

    private String[] staffsName;

    private String[] organizationsName;

    private String location;
}
