package com.ahu.ad.exam.vo;

import lombok.Data;

import java.util.List;

@Data
public class GroupVo {
    private Long id;
    private String gCode;
    private String gName;
    private String gStatus;
    private Long gParentId;
    private List<UserVo> gUsers;
}
