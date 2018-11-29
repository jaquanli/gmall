package com.atguigu.gmall.bean;

/**
 * 用来统一返回结果的实体类
 * @param <T> 要返回的具体的数据类型
 */
public class ResultEntity<T> {

    public static final String SUCCESS = "SUCCESS";

    public static final String FAILED = "FAILED";

    public static final String NO_MSG = "NO_MSG";

    private String result;//返回的结果，只有SUCCESS和FAILED两种

    private String massage = NO_MSG;//返回的消息信息

    private T date;//要返回的数据

    public ResultEntity() {

    }

    public ResultEntity(String result, String massage, T date) {
        this.result = result;
        this.massage = massage;
        this.date = date;
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

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
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
                ", massage='" + massage + '\'' +
                ", date=" + date +
                '}';
    }
}
