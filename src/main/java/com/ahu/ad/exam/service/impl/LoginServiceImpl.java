package com.ahu.ad.exam.service.impl;

import com.ahu.ad.common.response.Result;
import com.ahu.ad.exam.entity.User;
import com.ahu.ad.exam.mapper.UserMapper;
import com.ahu.ad.exam.service.LoginService;
import com.ahu.ad.exam.vo.UserVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LoginServiceImpl extends ServiceImpl<UserMapper, User> implements LoginService {

    @Resource
    private UserMapper userMapper;

    @Override
    public Result<UserVo> login(String username, String password)  {
        Result<UserVo> result = new Result<>();

//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("username",username);
//        User user = getBaseMapper().selectOne(queryWrapper);
        UserVo userVo = userMapper.getUserByName(username);
        if(userVo==null){
            result.setResultCode("201");
            result.setSuccess(false);
            result.setResultMessage("用户不存在");
        }else if("2".equals(userVo.getStatus())){
            result.setResultCode("201");
            result.setSuccess(false);
            result.setResultMessage("用户失效");
        }else{
            User user = userMapper.checkUsernameAndPassword(username,password);
            if(user==null){
                result.setResultCode("202");
                result.setSuccess(false);
                result.setResultMessage("密码错误");
            }else{
                result.setResultCode("100");
                result.setSuccess(true);
                result.setData(userVo);
            }
        }
        return result;
    }

    @Override
    public void register(User user) {
        user.setStatus("1");
        userMapper.insertUser(user);
        userMapper.insertUserRole(user.getId(),new Long(1));
    }

    @Override
    public UserVo getUser(String username) {
        return userMapper.getUserByName(username);
    }

    @Override
    public UserVo getUserWithRole(String username) {
        return userMapper.getUserWithRole(username);
    }
}
