package com.ahu.ad.exam.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@Data
public class ExamineVo {
    private Long id;
    private String hCode;
    private String hName;
    private String hSex;
    private String hAge;
    private String hEdu;
    private String result;
    private String evalScore;
    private String createDate;
    private String creator;
}
