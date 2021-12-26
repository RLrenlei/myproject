package com.rl.myproject.util;

/**
 * @author renlei
 * @date 2021/12/23 22:22
 * @description: 响应json结果
 */
public class ResponseResult<T> {

    private Integer state;
    private String message;
    private T data;

    public ResponseResult() {
        super();
    }

    public ResponseResult(Integer state) {
        super();
        this.state = state;
    }

    public ResponseResult(Integer state, T data) {
        super();
        this.state = state;
        this.data = data;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
