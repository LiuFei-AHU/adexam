package com.ahu.ad.exam.vo;

import com.ahu.ad.exam.entity.Blood;
import com.ahu.ad.exam.entity.Examine;
import com.ahu.ad.exam.entity.Mtlr;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class ExamineDataVo {
    @JsonProperty(value = "examine")
    private Examine examine;
    private Mtlr mtlr;
    private Blood blood;
}
