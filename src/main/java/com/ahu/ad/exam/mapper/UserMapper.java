package com.ahu.ad.exam.mapper;

import com.ahu.ad.exam.entity.Group;
import com.ahu.ad.exam.entity.Role;
import com.ahu.ad.exam.entity.User;
import com.ahu.ad.exam.vo.GroupTreeVo;
import com.ahu.ad.exam.vo.GroupVo;
import com.ahu.ad.exam.vo.UserVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserMapper extends BaseMapper<User> {
    User checkUsernameAndPassword(@Param("username") String username,@Param("password") String password);
    UserVo getUserWithRole(@Param("username") String username);
    List<Role> getUserRoles(@Param("username") String username);
    List<Map<String,String>> getAll(@Param("username") String username, @Param("rolename") String rolename,@Param("groupId") Long groupId);
    UserVo getUserByName(@Param("username") String username);
    void updateUser(UserVo userVo);
    Long insertUser(User user);
    void updateUserRole(@Param("userId") Long userId,@Param("roleId") Long roleId);
    Long insertUserRole(@Param("userId") Long userId,@Param("roleId") Long roleId);
    Long getRoleIdByCode(@Param("rolecode") String rolecode);

    Long getUserGroupId(@Param("userId") Long userId);
    GroupTreeVo getGroupTree(@Param("groupId") Long gid);

    GroupVo getGroupDataById(Long groupId);

    Long saveGroup(Group group);

    Long insertGroup(Group group);

    List<Group> getGroups(@Param("user") UserVo user,@Param("groupId") Long groupId);

    List<UserVo> getGroupUsersByGroupId(Long groupId);

    void updateGroupUser(UserVo user);

    void insertGroupUser(UserVo user);

    void delGroupUser(@Param("groupId") Long groupId, @Param("userId") Long userId);

    List<UserVo> getUsersExcludeGroup(Long groupId);

    void updatePassword(@Param("id")Long id,@Param("password") String password);

    Group getGroupParent(Long groupId);
}
