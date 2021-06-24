package com.ahu.ad.common;

import lombok.Data;

public class MyException extends Exception {

    public static final String INFO = "INFO";
    public static final String WARN = "WARN";
    public static final String ERROR = "ERROR";

    public static final String TEXT="TEXT";
    public static final String HTML="HTML";
    public static final String JSON="JSON";

    private String code;
    private String level;
    private String respType;

    public MyException(String code,String level,String message){
        super(message);
        this.code=code;
        this.level=level;
    }

    public MyException(String code,String level,String respType,String message){
        super(message);
        this.code=code;
        this.level=level;
        this.respType=respType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getRespType() {
        return respType;
    }

    public void setRespType(String respType) {
        this.respType = respType;
    }
}
