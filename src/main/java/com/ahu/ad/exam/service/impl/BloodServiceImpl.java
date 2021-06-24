package com.ahu.ad.exam.service.impl;

import com.ahu.ad.exam.entity.Blood;
import com.ahu.ad.exam.entity.User;
import com.ahu.ad.exam.mapper.BloodMapper;
import com.ahu.ad.exam.service.BloodService;
import com.ahu.ad.exam.vo.UserVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class BloodServiceImpl extends ServiceImpl<BloodMapper,Blood> implements BloodService {

    @Resource
    private BloodMapper bloodMapper;

    @Override
    public Long insertBlood(Blood blood) {
        return bloodMapper.insertBlood(blood);
    }

    @Override
    public void updateBlood(Blood blood) {
        bloodMapper.updateBlood(blood);
    }

    @Override
    public Long saveBlood(Blood blood, UserVo user) {
        if(blood!=null){
            if(blood.getId()!=null){
                blood.setUpdateId(user.getId());
                blood.setUpdateDate(new Date());
                bloodMapper.updateBlood(blood);
            }else {
                blood.setCreateDate(new Date());
                blood.setCreateId(user.getId());
                return bloodMapper.insertBlood(blood);
            }
        }
        return null;
    }
}
