package com.ahu.ad.exam.service;

import com.ahu.ad.exam.entity.Group;
import com.ahu.ad.exam.entity.User;
import com.ahu.ad.exam.vo.GroupVo;
import com.ahu.ad.exam.vo.UserVo;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
import java.util.Map;


public interface UserService extends IService<User> {
    List<Map<String,String>> getAll(String username, String rolename, UserVo user);
    UserVo getUserByName(String username);
    Long saveUser(UserVo userVo);
    public void updateUser(UserVo user);
    void updateUserRole(Long userId, Long roleId);
    Long insertUserRole(Long userId, Long roleId);
    Long getRoleIdByCode(String rolecode);
    Object getGroupTreeByUser(Long userId);

    GroupVo getGroupDataById(Long groupId);

    Long saveGroup(Group group);

    List<Group> getGroups(UserVo user, Long groupId);

    List<UserVo> getGroupUsersByGroupId(Long groupId);

    void saveGroupUser(UserVo user, String mode);

    void saveGroupUsers(List<UserVo> gUsers, String mode);

    void delGroupUser(Long groupId, Long userId);

    List<UserVo> getUsersExcludeGroup(Long groupId);

    boolean checkPassword(UserVo user, String passwod);

    void updatePassword(UserVo user, String password);

    Group getGroupParent(Long groupId);
}
