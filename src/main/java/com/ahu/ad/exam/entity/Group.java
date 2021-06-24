package com.ahu.ad.exam.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_group")
public class Group {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    private String gCode;
    private String gName;
    private String gStatus;
    private Long gParentId;
}
