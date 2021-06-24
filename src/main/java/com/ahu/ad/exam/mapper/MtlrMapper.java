package com.ahu.ad.exam.mapper;

import com.ahu.ad.exam.entity.Mtlr;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;


public interface MtlrMapper extends BaseMapper<Mtlr> {
    Long insertMtlr(Mtlr mtlr);
    void updateMtlr(Mtlr mtlr);
}
