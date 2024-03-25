package com.nebulashrine.thparty.entity.vo;

import com.nebulashrine.thparty.common.validGroup.CreateParty;
import com.nebulashrine.thparty.common.validGroup.DefaultParty;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    @Parameter(name = "id", description = "id")
    @NotNull(groups = {DefaultParty.class, CreateParty.class})
    private int id;

    @Parameter(name = "title", description = "聚会的标题")
    @NotNull(groups = CreateParty.class)
    private String title;

    @Parameter(name = "subTitle", description = "聚会的副标题")
    private String subTitle;

    @Parameter(name = "information", description = "聚会的详细信息")
    private String information;

    @Parameter(name = "startTime", description = "聚会的开始时间")
    @NotNull(groups = CreateParty.class)
    @Future(groups = CreateParty.class)
    private Date startTime;

    @Parameter(name = "endTime", description = "聚会的结束时间")
    @NotNull(groups = CreateParty.class)
    @Future(groups = CreateParty.class)
    private Date endTime;

    @Parameter(name = "domain", description = "聚会的二级域名")
    @Pattern(regexp = "^[a-zA-Z0-9]+$")
    @Size(min = 3, max = 16)
    private String domain;

    @Parameter(name = "leadersName", description = "聚会主催")
    @NotNull(groups = CreateParty.class)
    private String[] leadersName;

    @Parameter(name = "staffsName", description = "聚会斯达夫")
    private String[] staffsName;

    @Parameter(name = "organizationName", description = "与会社团")
    private String[] organizationsName;

    @Parameter(name = "location", description = "聚会地址")
    private String location;
}
