package com.atguigu.gmall.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 用来统一返回结果的实体类
 *
 * @param <T> 要返回的具体的数据类型
 */
public class ResultEntity<T> implements Serializable {

    public static final String SUCCESS = "SUCCESS";

    public static final String FAILED = "FAILED";

    public static final String NO_MSG = "NO_MSG";

    public static final String NO_DATA = "NO_DATA";

    private String result;//返回的结果，只有SUCCESS和FAILED两种

    private List<String> massageList;//返回的消息信息列表

    private String massage;//单条消息

    private T date;//要返回的数据

    public ResultEntity() {

    }

    public ResultEntity(String result, List<String> massageList, String massage, T date) {
        this.result = result;
        this.massageList = massageList;
        this.massage = massage;
        this.date = date;
    }

    public List<String> getMassageList() {
        return massageList;
    }

    public void setMassageList(List<String> massageList) {
        this.massageList = massageList;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public static String getSUCCESS() {
        return SUCCESS;
    }

    public static String getFAILED() {
        return FAILED;
    }

    public static String getNoMsg() {
        return NO_MSG;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }


    public T getDate() {
        return date;
    }

    public void setDate(T date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ResultEntity{" +
                "result='" + result + '\'' +
                ", massageList=" + massageList +
                ", massage='" + massage + '\'' +
                ", date=" + date +
                '}';
    }
}
