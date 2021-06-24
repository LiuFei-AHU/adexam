package com.ahu.ad.exam.service.impl;

import com.ahu.ad.common.CommonUtl;
import com.ahu.ad.exam.entity.*;
import com.ahu.ad.exam.mapper.ExamMapper;
import com.ahu.ad.exam.mapper.UserMapper;
import com.ahu.ad.exam.service.ExamineService;
import com.ahu.ad.exam.vo.ExamineVo;
import com.ahu.ad.exam.vo.ExamineDataVo;
import com.ahu.ad.exam.vo.UserVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ExamineServiceImpl extends ServiceImpl<ExamMapper,Examine> implements ExamineService{

    @Resource
    private ExamMapper examMapper;

    @Resource
    private UserMapper userMapper;

    public List<Map<String,String>> getAllExamine(String hcode, String hname,UserVo user){
        //判断用户角色 TODO
        /*
        String uname = user.getUsername();
        List<Role> roles = userMapper.getUserRoles(user.getUsername());
        for (Role role: roles) {
            if("admin".equals(role.getRolecode())){
                uname = "";
                break;
            }
        }*/
        if(user.getGroupId()==null){
            return null;
        }
        if("1".equals(user.getIsAdmin()))
            return examMapper.getAllExamineOfAdmin(hcode,hname,user);
        return examMapper.getAllExamine(hcode,hname,user);
    }

    @Override
    public Mtlr getMtlrDataByCode(String hcode) {
        return examMapper.getMtlrDataByCode(hcode);
    }

    @Override
    public Blood getBloodDataByCode(String hcode) {
        return examMapper.getBloodDataByCode(hcode);
    }

    @Override
    public ExamineVo getBasicDataByCode(String hcode) {
        return examMapper.getBasicDataByCode(hcode);
    }

    @Override
    public Long getIdByCode(String hcode) {
        return examMapper.getIdByCode(hcode);
    }

    @Override
    public String getNextCode() {
       return CommonUtl.getNextCode(examMapper.getLastCode());
    }

    @Override
    public Long insertExamine(Examine examine) {
        return examMapper.insertExamine(examine);
    }

    @Override
    public void updateExamine(Examine examine) {
        examMapper.updateExamine(examine);
    }

    @Override
    public Long saveExamine(Examine examine,UserVo user) {
        if(examine!=null){
            if(examine.getId()!=null){
                examine.setUpdateId(user.getId());
                examine.setUpdateDate(new Date());
                examMapper.updateExamine(examine);
            }else {
                examine.setCreateDate(new Date());
                examine.setCreateId(user.getId());
                examine.setGroupId(user.getGroupId());
                return examMapper.insertExamine(examine);
            }
        }
        return null;
    }

}
