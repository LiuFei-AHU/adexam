package com.ahu.ad.common;


import org.apache.commons.lang3.StringUtils;

public class CommonUtl {
    public static String getNextCode(String code){
        String next = "";
        if(StringUtils.isNotEmpty(code)){
            String seq = code.substring(1);
            int no = Integer.valueOf(seq);
            seq = String.valueOf(no+1);
            next =  "H"+StringUtils.leftPad(seq,6,"0");
        }else{
            next = "H000001";
        }
        return next;
    }
}
