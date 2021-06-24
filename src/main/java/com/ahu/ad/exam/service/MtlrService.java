package com.ahu.ad.exam.service;

import com.ahu.ad.exam.entity.Examine;
import com.ahu.ad.exam.entity.Mtlr;
import com.ahu.ad.exam.entity.User;
import com.ahu.ad.exam.vo.UserVo;
import com.baomidou.mybatisplus.extension.service.IService;


public interface MtlrService extends IService<Mtlr> {
    Long insertMtlr(Mtlr mtlr);
    void updateMtlr(Mtlr mtlr);
    Long saveMtlr(Mtlr mtlr, UserVo user);
}
