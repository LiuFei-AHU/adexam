package com.ahu.ad.exam.controller;

import com.ahu.ad.common.ExamUtl;
import com.ahu.ad.common.MyException;
import com.ahu.ad.common.response.Result;
import com.ahu.ad.exam.entity.Blood;
import com.ahu.ad.exam.entity.Examine;
import com.ahu.ad.exam.entity.Mtlr;
import com.ahu.ad.exam.entity.User;
import com.ahu.ad.exam.service.BloodService;
import com.ahu.ad.exam.service.ExamineService;
import com.ahu.ad.exam.service.MtlrService;
import com.ahu.ad.exam.vo.ExamineVo;
import com.ahu.ad.exam.vo.ExamineDataVo;
import com.ahu.ad.exam.vo.UserVo;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.util.Date;


@RestController
@RequestMapping("/exam")
@Slf4j
public class ExamController {

    @Autowired
    ExamineService examineService;

    @Autowired
    MtlrService mtlrService;

    @Autowired
    BloodService bloodService;

    @GetMapping("/list")
    public Object list(HttpServletRequest request,@RequestParam("hcode") String hcode,@RequestParam("hname") String hname){
        UserVo user = (UserVo) request.getSession().getAttribute("user");
        return JSON.toJSON(examineService.getAllExamine(hcode,hname,user));
    }

    @GetMapping("/add")
    public Object add(HttpServletRequest request,ModelMap map,@RequestParam(value = "action",defaultValue = "view") String action) throws Exception {
        //map.addAttribute("hcode", "tmp"+System.currentTimeMillis());
        UserVo user = (UserVo) request.getSession().getAttribute("user");
        if(user.getGroupId()==null)
            throw new MyException("201",MyException.ERROR,"请先加入工作组！");
        map.addAttribute("hcode", examineService.getNextCode());
        map.addAttribute("action",action);
        map.addAttribute("exam",new ExamineVo());
        map.addAttribute("mtlr",new Mtlr());
        map.addAttribute("blood",new Blood());
        return new ModelAndView("exam/blxx");
    }

    @GetMapping("/blxx")
    public Object main(ModelMap map,@RequestParam("hcode") String hcode,
                       @RequestParam(value = "action",defaultValue = "view") String action){
        map.addAttribute("hcode",hcode);
        map.addAttribute("action",action);

        ExamineVo exam = examineService.getBasicDataByCode(hcode);
        if(exam!=null){
            map.addAttribute("exam",exam);
        }

        Mtlr mtlr = examineService.getMtlrDataByCode(hcode);
        if(mtlr==null){
            mtlr = new Mtlr();
        }
        map.addAttribute("mtlr",mtlr);

        Blood blood = examineService.getBloodDataByCode(hcode);
        if(blood==null){
            blood= new Blood();
        }
        map.addAttribute("blood",blood);

        return new ModelAndView("exam/blxx");
    }

    @PostMapping("/save")
    @Transactional
    public Object save(HttpServletRequest request, @RequestBody String json) throws Exception {
        System.out.println(json);
        UserVo user = (UserVo) request.getSession().getAttribute("user");
        if(user.getGroupId()==null)
            throw new MyException("201",MyException.ERROR,"请先加入工作组！");
        ExamineDataVo data = JSON.parseObject(json,ExamineDataVo.class);
        String result;
        try{
            //判断风险等级
            result = ExamUtl.calculateResult(data.getExamine(),data.getMtlr(),data.getBlood());
            data.getExamine().setResult(result);
            examineService.saveExamine(data.getExamine(),user);
            Long id = data.getExamine().getId();
            System.out.println("new id is :"+id);
            data.getBlood().setExamId(id);
            data.getMtlr().setExamId(id);
            bloodService.saveBlood(data.getBlood(),user);
            mtlrService.saveMtlr(data.getMtlr(),user);
        }catch (Exception e){
            if(e.getClass() != MyException.class){
                e.printStackTrace();
            }
            return Result.ofError("101",e.getMessage());
        }
        if(!ExamUtl.NO_ENOUGH_INFO.equals(result)){
            return Result.ofSuccess(result);
        }
        return Result.ofSuccess(result);
    }

    @GetMapping("/basic")
    public Object basic(ModelMap map,@RequestParam(value = "hcode",required = false) String hcode){
        ExamineVo exam = examineService.getBasicDataByCode(hcode);
        if(exam!=null){
            map.addAttribute("exam",exam);
        }
        return new ModelAndView("exam/basic");
    }

    @GetMapping("/mtlr")
    public Object mtlr(ModelMap map,@RequestParam(value = "hcode",required = false) String hcode){
        Mtlr mtlr = examineService.getMtlrDataByCode(hcode);
        if(mtlr!=null){
            map.addAttribute("mtlr",mtlr);
        }
        return new ModelAndView("exam/mtlr");
    }

    @GetMapping("/xyjc")
    public Object xyjc(ModelMap map,@RequestParam(value = "hcode", required = false) String hcode){
        Blood blood = examineService.getBloodDataByCode(hcode);
        if(blood!=null){
            map.addAttribute("blood",blood);
        }
        return new ModelAndView("exam/xyjc");
    }


}
