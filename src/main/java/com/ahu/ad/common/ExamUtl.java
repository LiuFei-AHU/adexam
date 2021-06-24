package com.ahu.ad.common;


import com.ahu.ad.exam.entity.Blood;
import com.ahu.ad.exam.entity.Examine;
import com.ahu.ad.exam.entity.Mtlr;
import org.apache.commons.lang3.StringUtils;

public class ExamUtl {
    public static final String HIGHT_RISK = "高风险";
    public static final String NOMAL_RISK = "中风险";
    public static final String LOW_RISK = "低风险";
    public static final String NO_ENOUGH_INFO = "体检信息不足，请补全";

    public static Double[] calculateP(Examine e, Mtlr m, Blood b){
        Double p = 0.0;
        Double q = 0.0;
        Double t = 0.0;

        if(e.getHAge()!=null){
            if(e.getHAge()>=60){
                p+=0.1;
            }
        }else{
            q+=0.1;
        }

        if(!StringUtils.isBlank(e.getHEdu())){
            if(("1".equals(e.getHEdu()) || "2".equals(e.getHEdu()))){
                p+=0.4;
            }
        }else{
            q+=0.4;
        }
        if(b!=null){
            if(!StringUtils.isBlank(b.getHscrp())){
                t = Double.valueOf(b.getHscrp());
                if(t>=3){
                    p+=0.25;
                }
            }else{
                q+=0.25;
            }

            if(!StringUtils.isBlank(b.getCreatinine())){
                t = Double.valueOf(b.getCreatinine());
                if(t<53 || t>115){
                    p+=0.16;
                }
            }else{
                q+=0.16;
            }

            if(!StringUtils.isBlank(b.getCholesterol())){
                t = Double.valueOf(b.getCholesterol());
                if(t<3.5||t>6.5){
                    p+=0.6;
                }
            }else{
                q+=0.6;
            }

            if(!StringUtils.isBlank(b.getTriglyceride())){
                t = Double.valueOf(b.getTriglyceride());
                if(t<0.6||t>1.8){
                    p+=0.38;
                }
            }else{
                q+=0.38;
            }

            if(!StringUtils.isBlank(b.getBloodGlucose())){
                t = Double.valueOf(b.getBloodGlucose());
                if(t<4.0||t>5.9){
                    p+=0.68;
                }
            }else{
                q+=0.68;
            }

            if(!StringUtils.isBlank(b.getBmi())){
                t = Double.valueOf(b.getBmi());
                if(t<18.5||t>30){
                    p+=0.67;
                }
            }else{
                q+=0.67;
            }

            if(!StringUtils.isBlank(b.getPulse())){
                t = Double.valueOf(b.getPulse());
                if(t<60||t>100){
                    p+=0.78;
                }
            }else{
                q+=0.78;
            }

            if(!StringUtils.isBlank(b.getSystolicBp())){
                t = Double.valueOf(b.getSystolicBp());
                if(t>120){
                    p+=0.08;
                }
            }else{
                q+=0.08;
            }

            if(!StringUtils.isBlank(b.getDiastolicBp())){
                t = Double.valueOf(b.getDiastolicBp());
                if(t>80){
                    p+=0.65;
                }
            }else{
                q+=0.65;
            }

            if(!StringUtils.isBlank(b.getDiabetes())){
                if("有".equals(b.getDiabetes())){
                    p+=0.45;
                }
            }else{
                q+=0.45;
            }

            if(!StringUtils.isBlank(b.getMyocardialInfarction())){
                if("有".equals(b.getMyocardialInfarction())){
                    p+=0.24;
                }
            }else{
                q+=0.24;
            }

            if(!StringUtils.isBlank(b.getAnginaPectoris())){
                if("有".equals(b.getAnginaPectoris())){
                    p+=0.53;
                }
            }else{
                q+=0.53;
            }

            if(!StringUtils.isBlank(b.getStroke())){
                if("有".equals(b.getStroke())){
                    p+=0.43;
                }
            }else{
                q+=0.43;
            }

            if(!StringUtils.isBlank(b.getSmoker())){
                if("有".equals(b.getSmoker())){
                    p+=0.16;
                }
            }else{
                q+=0.16;
            }

            if(!StringUtils.isBlank(b.getSubjectiveHealthStatus())){
                if("1".equals(b.getSubjectiveHealthStatus()) || "2".equals(b.getSubjectiveHealthStatus())){
                    p+=0.27;
                }
            }else{
                q+=0.27;
            }
        }

        return new Double[]{p,q};
    }

    public static String calculateResult(Examine examine, Mtlr mtlr, Blood blood) throws Exception {
        String result = ExamUtl.LOW_RISK;
//        Double score = null;
        if(mtlr.getTotalScore()!=null && mtlr.getTotalScore()<26){
            result=ExamUtl.HIGHT_RISK;
        }else{
            Double[] pq = calculateP(examine,mtlr,blood);
            Double p = pq[0];
            Double q = pq[1];
            if(p<2 && p+q>=2){
                result = ExamUtl.NO_ENOUGH_INFO;
            }
            if(p>2.0 && p<3.0){
                result=ExamUtl.NOMAL_RISK;
            }
            if(p>3.0){
                result=ExamUtl.HIGHT_RISK;
            }
//            score = p;
        }
        return result;
    }

}
