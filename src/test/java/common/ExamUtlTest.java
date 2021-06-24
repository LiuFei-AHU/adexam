package common;

import com.ahu.ad.BootApplication;
import com.ahu.ad.common.CommonUtl;
import com.ahu.ad.common.ExamUtl;
import com.ahu.ad.exam.entity.Blood;
import com.ahu.ad.exam.entity.Examine;
import com.ahu.ad.exam.entity.Mtlr;
import com.ahu.ad.exam.service.UserService;
import com.ahu.ad.exam.service.impl.UserServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,classes = BootApplication.class)
public class ExamUtlTest {

    @Test
    public void test(){
        Examine e = new Examine();
        Mtlr m = new Mtlr();
        Blood b = new Blood();

        m.setTotalScore(26.0);
        try{
            String result = ExamUtl.calculateResult(e,m,b);
            System.out.println(result);
        }catch (Exception ee){
            ee.printStackTrace();
        }
    }

    @Test
    public void test1(){

        String S = CommonUtl.getNextCode("H000009");
        System.out.println(S);
    }

    @Test
    public void getGroupTreeByUser(){
        UserService us = new UserServiceImpl();
        Object list = us.getGroupTreeByUser(2l);
        System.out.println(list);
    }
}
