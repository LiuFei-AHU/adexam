package com.ahu.ad.exam.service;

import com.ahu.ad.exam.entity.Blood;
import com.ahu.ad.exam.entity.User;
import com.ahu.ad.exam.vo.UserVo;
import com.baomidou.mybatisplus.extension.service.IService;


public interface BloodService extends IService<Blood> {
    Long insertBlood(Blood blood);
    void updateBlood(Blood blood);
    Long saveBlood(Blood blood, UserVo user);
}