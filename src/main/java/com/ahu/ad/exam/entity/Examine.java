package com.ahu.ad.exam.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;


@Data
@TableName("t_examine")
public class Examine {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    private String hCode;
    private String hName;
    private String hSex;
    private Integer hAge;
    private String hEdu;
    private String result;
    private String evalScore;
    private Date createDate;
    private Long createId;
    private Long updateId;
    @TableField(update = "SYSDATE")
    private Date updateDate;
    private Long groupId;
}
