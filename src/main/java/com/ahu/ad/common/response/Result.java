package com.ahu.ad.common.response;

import lombok.Data;

/**
 * @Description
 * @Author feiliu6
 * @Date 13:15 2021/2/5
 * @Version 1.0
 */
@Data
public class Result<T> {
    private boolean success = true;
    private String resultCode;
    private long timestamp;
    private String resultMessage;
    private T data;

    public Result(){
        timestamp = System.currentTimeMillis();
    }


    public static <T>Result ofSuccess(T content){
        Result result = new Result();
        result.setResultCode("100");
        result.setData(content);
        return result;
    }

    public static <T>Result ofError(String resultCode,String message){
        Result result = new Result();
        result.setResultCode(resultCode);
        result.setSuccess(false);
        result.setResultMessage(message);
        return result;
    }

    public static <T>Result ofError(String resultCode,String message,T content){
        Result result = new Result();
        result.setResultCode(resultCode);
        result.setSuccess(false);
        result.setResultMessage(message);
        result.setData(content);
        return result;
    }

}
