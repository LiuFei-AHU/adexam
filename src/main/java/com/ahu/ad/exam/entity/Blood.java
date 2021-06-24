package com.ahu.ad.exam.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;


@Data
@TableName("t_ex_blood")
public class Blood {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    @JSONField(format="yyyy/MM/dd")
    private Date examDate;
    private Long examId;
    private String hscrp;
    private String creatinine;
    private String cholesterol;
    private String triglyceride;
    private String bloodGlucose;
    private String bmi;
    private String pulse;
    private String systolicBp;
    private String diastolicBp;
    private String diabetes;
    private String myocardialInfarction;
    private String anginaPectoris;
    private String stroke;
    private String smoker;
    private String subjectiveHealthStatus;
    private String descr;
    private Long createId;
    private Date createDate;
    private Long updateId;
    @TableField(update = "SYSDATE")
    private Date updateDate;

}
