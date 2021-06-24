package com.ahu.ad.exam.mapper;

import com.ahu.ad.exam.entity.Blood;
import com.ahu.ad.exam.entity.Examine;
import com.ahu.ad.exam.entity.Mtlr;
import com.ahu.ad.exam.vo.ExamineVo;
import com.ahu.ad.exam.vo.UserVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface ExamMapper extends BaseMapper<Examine> {
    List<Map<String,String>> getAllExamine(@Param("hcode") String hcode, @Param("hname") String hname,
                                           @Param("username") String username);
    List<Map<String,String>> getAllExamine(@Param("hcode") String hcode, @Param("hname") String hname,
                                           @Param("user") UserVo user);
    List<Map<String,String>> getAllExamineOfAdmin(@Param("hcode") String hcode, @Param("hname") String hname,
                                                  @Param("user") UserVo user);
    Mtlr getMtlrDataByCode(@Param("hcode") String hcode);
    Blood getBloodDataByCode(@Param("hcode") String hcode);
    ExamineVo getBasicDataByCode(@Param("hcode") String hcode);
    Long getIdByCode(@Param("hcode") String hcode);
    String getLastCode();
    Long insertExamine(Examine examine);
    void updateExamine(Examine examine);
}
