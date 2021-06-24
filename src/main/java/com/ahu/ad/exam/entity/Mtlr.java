package com.ahu.ad.exam.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.text.SimpleDateFormat;
import java.util.Date;


@Data
@TableName("t_ex_mtlr")
public class Mtlr {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    @JSONField(format="yyyy/MM/dd")
    private Date examDate;
    private Long examId;
    private Double totalScore;
    private Long createId;
    private Date createDate;
    private Long updateId;
    @TableField(update = "SYSDATE")
    private Date updateDate;

    public Date getExamDate(){
        if(examDate==null){
            examDate = new Date();
        }
        return examDate;
    }
}
