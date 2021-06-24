package com.ahu.ad.exam.vo;

import com.ahu.ad.exam.entity.Group;
import lombok.Data;

@Data
public class UserVo {
    private Long id;
    private String username;
    private String password;
    private String status;
    private String statusDescr;
    private String empcode;
    private String rolecode;
    private String rolename;
    private String isAdmin;//工作组管理员
    private Long groupId;//工作组
    private String groupName;//工作组名称
    private String groupCode;
    private Long rootGroupId;//根工作组

    public boolean isSysAdmin(){
        return "admin".equals(rolecode);
    }
    public boolean isGroupAdmin(){
        return "1".equals(isAdmin);
    }
}
