package com.ahu.ad.exam.service.impl;


import com.ahu.ad.exam.entity.Group;
import com.ahu.ad.exam.entity.User;
import com.ahu.ad.exam.mapper.UserMapper;
import com.ahu.ad.exam.service.UserService;
import com.ahu.ad.exam.vo.GroupTreeVo;
import com.ahu.ad.exam.vo.GroupVo;
import com.ahu.ad.exam.vo.UserVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService{

    @Resource
    private
    UserMapper userMapper;


    @Override
    public List<Map<String, String>> getAll(String username, String rolename, UserVo user) {
        return userMapper.getAll(username,rolename,user.getGroupId());
    }

    @Override
    public UserVo getUserByName(String username) {
        return userMapper.getUserByName(username);
    }

    @Override
    public Long saveUser(UserVo userVo) {
        if(userVo!=null){
            Long roleId = userMapper.getRoleIdByCode(userVo.getRolecode());
            if(userVo.getId()!=null){
                userMapper.updateUser(userVo);
                userMapper.updateUserRole(userVo.getId(),roleId);
            }else{
                User user = new User();
                user.setUsername(userVo.getUsername());
                user.setPassword(userVo.getPassword());
                user.setStatus(userVo.getStatus());
                user.setRootGroupId(userVo.getRootGroupId());
                user.setPassword("000000");
                userMapper.insertUser(user);
                userMapper.insertUserRole(user.getId(),roleId);
                //默认加入根工作组
                userVo.setId(user.getId());
                userVo.setIsAdmin("2");
                userVo.setGroupId(userVo.getRootGroupId());
                userMapper.insertGroupUser(userVo);
            }
        }
        return null;
    }

    public void updateUser(UserVo user){
        userMapper.updateUser(user);
    }

    @Override
    public void updateUserRole(Long userId, Long roleId) {
        userMapper.updateUserRole(userId,roleId);
    }

    @Override
    public Long insertUserRole(Long userId, Long roleId) {
        return userMapper.insertUserRole(userId,roleId);
    }

    @Override
    public Long getRoleIdByCode(String rolecode) {
        return null;
    }

    @Override
    public Object getGroupTreeByUser(Long userId) {
        Long gid = userMapper.getUserGroupId(userId);
        GroupTreeVo userGroups = null;
        if(gid != null)
            userGroups = userMapper.getGroupTree(gid);
        return userGroups;
    }

    @Override
    public GroupVo getGroupDataById(Long groupId) {
        return userMapper.getGroupDataById(groupId);
    }

    @Override
    public Long saveGroup(Group group) {
        if(group.getId()!=null)
            return userMapper.saveGroup(group);
        return userMapper.insertGroup(group);
    }

    @Override
    public List<Group> getGroups(UserVo user, Long groupId) {
        return userMapper.getGroups(user,groupId);
    }

    @Override
    public List<UserVo> getGroupUsersByGroupId(Long groupId) {
        List<UserVo> users = userMapper.getGroupUsersByGroupId(groupId);
        if(users==null || users.isEmpty()){
            users = (users==null)? new ArrayList<>(1):users;
            UserVo user = new UserVo();
            user.setUsername("-");
            user.setStatus("-");
            user.setIsAdmin("2");
            users.add(user);
        }
        return users;
    }

    @Override
    public void saveGroupUsers(List<UserVo> gUsers, String mode) {
        for (UserVo user:gUsers) {
            saveGroupUser(user, mode);
        }
    }

    @Override
    public void delGroupUser(Long groupId, Long userId) {
        userMapper.delGroupUser(groupId,userId);
    }

    @Override
    public List<UserVo> getUsersExcludeGroup(Long groupId) {
        return userMapper.getUsersExcludeGroup(groupId);
    }

    @Override
    public boolean checkPassword(UserVo user, String passwod) {
        return userMapper.checkUsernameAndPassword(user.getUsername(), passwod) != null;
    }

    @Override
    public void updatePassword(UserVo user, String password) {
        userMapper.updatePassword(user.getId(),password);
    }

    @Override
    public Group getGroupParent(Long groupId) {
        return userMapper.getGroupParent(groupId);
    }

    @Override
    public void saveGroupUser(UserVo user, String mode) {
        if("U".equals(mode)){
            userMapper.updateGroupUser(user);
        }else{
            userMapper.insertGroupUser(user);
        }
    }

}
