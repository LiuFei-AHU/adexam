package com.ahu.ad.exam.service.impl;

import com.ahu.ad.exam.entity.Mtlr;
import com.ahu.ad.exam.entity.User;
import com.ahu.ad.exam.mapper.MtlrMapper;
import com.ahu.ad.exam.service.MtlrService;
import com.ahu.ad.exam.vo.UserVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class MtlrServiceImpl extends ServiceImpl<MtlrMapper,Mtlr> implements MtlrService {

    @Resource
    private MtlrMapper mtlrMapper;

    @Override
    public Long insertMtlr(Mtlr mtlr) {
        return mtlrMapper.insertMtlr(mtlr);
    }

    @Override
    public void updateMtlr(Mtlr mtlr) {
        mtlrMapper.updateMtlr(mtlr);
    }

    @Override
    public Long saveMtlr(Mtlr mtlr, UserVo user) {
        if(mtlr!=null){
            if(mtlr.getId()!=null){
                mtlr.setUpdateId(user.getId());
                mtlr.setUpdateDate(new Date());
                mtlrMapper.updateMtlr(mtlr);
            }else {
                mtlr.setCreateDate(new Date());
                mtlr.setCreateId(user.getId());
                return mtlrMapper.insertMtlr(mtlr);
            }
        }
        return null;
    }

}
