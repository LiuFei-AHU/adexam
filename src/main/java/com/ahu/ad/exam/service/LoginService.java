package com.ahu.ad.exam.service;

import com.ahu.ad.common.response.Result;
import com.ahu.ad.exam.entity.User;
import com.ahu.ad.exam.vo.UserVo;
import com.baomidou.mybatisplus.extension.service.IService;

public interface LoginService extends IService<User> {
    Result<UserVo> login(String username, String password);
    void register(User user);
    UserVo getUser(String username);
    UserVo getUserWithRole(String username);
}
