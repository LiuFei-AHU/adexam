package com.ahu.ad.exam.controller;

import com.ahu.ad.common.MyException;
import com.ahu.ad.common.response.Result;
import com.ahu.ad.exam.entity.Group;
import com.ahu.ad.exam.entity.User;
import com.ahu.ad.exam.service.LoginService;
import com.ahu.ad.exam.service.UserService;
import com.ahu.ad.exam.vo.GroupTreeVo;
import com.ahu.ad.exam.vo.GroupVo;
import com.ahu.ad.exam.vo.UserVo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.statement.execute.Execute;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/changepwd")
    public Object changePassword(){
        return new ModelAndView("user/changePwd");
    }

    @PostMapping("/changepwd")
    public Object changePassword(HttpServletRequest request,@RequestBody Map<String,Object> param){
        UserVo user = (UserVo) request.getSession().getAttribute("user");
        try{
            userService.updatePassword(user,(String)param.get("newPassword"));
        }catch (Exception e){
            e.printStackTrace();
            return Result.ofError("101","修改密码出错");
        }
        return Result.ofSuccess("");
    }

    @PostMapping("/checkpwd")
    public Object checkPassword(HttpServletRequest request,@RequestBody Map<String,Object> param){
        UserVo user = (UserVo) request.getSession().getAttribute("user");
        if(!userService.checkPassword(user, (String) param.get("password"))){
            return Result.ofError("101","当前密码输入错误");
        }
        return Result.ofSuccess("");
    }

    @GetMapping("/manage")
    public Object manage(){
        return new ModelAndView("user/manage");
    }

    @GetMapping("/list")
    public Object list(HttpServletRequest request, @RequestParam("username") String username, @RequestParam("rolename") String rolename) {
        UserVo user = (UserVo) request.getSession().getAttribute("user");
        if(!user.isGroupAdmin())
            return null;
        return JSON.toJSON(userService.getAll(username,rolename,user));
    }

    @GetMapping("/info")
    public Object info(ModelMap map, @RequestParam("username") String username,
                       @RequestParam(value = "action",defaultValue = "view") String action){
        map.addAttribute("username",username);
        map.addAttribute("action",action);

        UserVo userVo = userService.getUserByName(username);
        if(userVo==null){
            userVo = new UserVo();
        }
        map.addAttribute("uservo",userVo);

        return new ModelAndView("user/info");
    }

    @GetMapping("/add")
    public Object add(ModelMap map,HttpServletRequest request) throws Exception {
        UserVo user = (UserVo) request.getSession().getAttribute("user");
        if(user.getRootGroupId() == null){
            throw new MyException("201",MyException.WARN,MyException.TEXT,"请先创建工作组");
            //return "请先创建工作组";
        }
        map.addAttribute("uservo",user);
        map.addAttribute("rootGroupId",user.getRootGroupId());
        return new ModelAndView("user/add");
    }

    @PostMapping("/save")
    @Transactional
    public Object save(HttpServletRequest request,@RequestBody String json){
        System.out.println(json);
        UserVo user = (UserVo) request.getSession().getAttribute("user");
        UserVo userVo = JSON.parseObject(json,UserVo.class);
        userVo.setRootGroupId(user.getRootGroupId());
        //检查用户名
        UserVo user1 = userService.getUserByName(userVo.getUsername());
        if(user1!=null && user1.getId()!=null){
            return Result.ofError("101","用户名已存在");
        }
        try{
            userService.saveUser(userVo);
        }catch (Exception e){
            e.printStackTrace();
            return Result.ofError("101",e.getMessage());
        }
        return Result.ofSuccess("");
    }

    @GetMapping("/group")
    public Object Group(HttpServletRequest request) throws Exception {
        UserVo user = (UserVo) request.getSession().getAttribute("user");
        if(user.getGroupId()!=null && !user.isGroupAdmin())
            throw new MyException("201",MyException.WARN,"您没有工作组管理权限！");
        return new ModelAndView("user/work_group");
    }

    @GetMapping("/tree")
    public Object getTree(HttpServletRequest request){
        UserVo user = (UserVo) request.getSession().getAttribute("user");
        Object tree = userService.getGroupTreeByUser(user.getId());
        return JSON.toJSON(tree);
    }

    @GetMapping("/group/page/{action}/{groupId}")
    public Object groupPage(HttpServletRequest request,ModelMap map,@PathVariable("action") String action , @PathVariable("groupId") Long groupId){
        UserVo user = (UserVo) request.getSession().getAttribute("user");
        GroupVo group = null;
        if(groupId != null && groupId>0){
            group = userService.getGroupDataById(groupId);
        }else{
            group = new GroupVo();
        }
        List<Group> groups = null;
        if("add".equals(action)){
            groups = userService.getGroups(user,groupId);
        }else{
            groups = new ArrayList<>(1);
            Group parent = userService.getGroupParent(groupId);
            if(parent!=null && parent.getId()!=null)
                groups.add(parent);
        }
        map.addAttribute("action",action);
        map.addAttribute("group",group);
        map.addAttribute("groups",groups);
        return new ModelAndView("user/group_info");
    }

    @PostMapping("/group/save")
    @Transactional
    public Object saveGroupPage(HttpServletRequest request,@RequestBody String json){
        UserVo user = (UserVo) request.getSession().getAttribute("user");
        GroupVo groupVo = JSON.parseObject(json, GroupVo.class);
        Group group = new Group();
        try {
            BeanUtils.copyProperties(group,groupVo);
        } catch (IllegalAccessException|InvocationTargetException e) {
            e.printStackTrace();
        }
        try{
            userService.saveGroup(group);
            List<UserVo> users = groupVo.getGUsers();
            //如果是在当前用户管理的工作组下创建组，则不保存工作组和用户的映射
            if(users == null || users.isEmpty()){
                if(!user.isGroupAdmin()){
                    user.setIsAdmin("1");
                    user.setGroupId(group.getId());
                    user.setGroupCode(group.getGCode());
                    user.setGroupName(group.getGName());
                    userService.saveGroupUser(user,"I");
                }
                //创建根工作组，需更新用户
                if(user.getRootGroupId()==null)
                    user.setRootGroupId(group.getId());
                userService.updateUser(user);
                request.getSession().setAttribute("user",user);
            }else{
                userService.saveGroupUsers(users,"U");
            }
        }catch (Exception e){
            e.printStackTrace();
            return Result.ofError("101",e.getMessage());
        }
        return Result.ofSuccess(group);
    }

    @GetMapping("/group/users")
    public Object getGroupUsersByGroupId( @RequestParam("groupId") Long groupId){
        if(groupId==null)
            return null;
        return JSON.toJSON(userService.getGroupUsersByGroupId(groupId));
    }

    @GetMapping("/group/users/{groupId}")
    public Object getGroupUsers(HttpServletRequest request,ModelMap map, @PathVariable("groupId") Long groupId){
        UserVo user = (UserVo) request.getSession().getAttribute("user");
        if(user.getGroupId()!=null){
            List<UserVo> users= userService.getUsersExcludeGroup(user.getGroupId());
            map.addAttribute("users",users);
        }
        return new ModelAndView("user/choose_user");
    }

    @DeleteMapping("/group/users/del/{groupId}/{userId}")
    public Object delGroupUser(HttpServletRequest request,@PathVariable("groupId") Long groupId,@PathVariable("userId") Long userId){
        UserVo user = (UserVo) request.getSession().getAttribute("user");
        try{
            if(user.getId().equals(userId)){
                return Result.ofError("101","禁止删除当前工作组用户！");
            }
            userService.delGroupUser(groupId,userId);
        }catch (Exception e){
            e.printStackTrace();
            return Result.ofError("101",e.getMessage());
        }
        return Result.ofSuccess("");
    }

    @PostMapping("/group/users/add/{groupId}/{userIds}")
    @Transactional
    public Object addGroupUser(@PathVariable("groupId") String groupId,@PathVariable("userIds") String userId){
        try{
            if(StringUtils.isNotEmpty(userId)){
                String[] userids = userId.split(",");
                for(String uid:userids){
                    UserVo userVo = new UserVo();
                    userVo.setIsAdmin("2");
                    userVo.setId(Long.valueOf(uid));
                    userVo.setGroupId(Long.valueOf(groupId));
                    userService.saveGroupUser(userVo,"I");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return Result.ofError("101",e.getMessage());
        }
        return Result.ofSuccess("");
    }

}
