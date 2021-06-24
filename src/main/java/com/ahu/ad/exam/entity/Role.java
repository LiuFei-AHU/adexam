package com.ahu.ad.exam.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_role")
public class Role {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    private String rolecode;
    private String rolename;
    private String descr;
}
