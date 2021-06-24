package com.ahu.ad.exam.mapper;

import com.ahu.ad.exam.entity.Blood;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;


public interface BloodMapper extends BaseMapper<Blood> {
    Long insertBlood(Blood blood);
    void updateBlood(Blood blood);
}
