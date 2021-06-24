package com.ahu.ad.exam.service;

import com.ahu.ad.exam.entity.Blood;
import com.ahu.ad.exam.entity.Examine;
import com.ahu.ad.exam.entity.Mtlr;
import com.ahu.ad.exam.entity.User;
import com.ahu.ad.exam.vo.ExamineVo;
import com.ahu.ad.exam.vo.ExamineDataVo;
import com.ahu.ad.exam.vo.UserVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

public interface ExamineService  extends IService<Examine> {
    List<Map<String,String>> getAllExamine(String hcode, String hname,UserVo user);
    Mtlr getMtlrDataByCode(String hcode);
    Blood getBloodDataByCode(String hcode);
    ExamineVo getBasicDataByCode(String hcode);
    Long getIdByCode(String hcode);
    String getNextCode();
    Long insertExamine(Examine examine);
    void updateExamine(Examine examine);
    Long saveExamine(Examine examine,UserVo user);
}
