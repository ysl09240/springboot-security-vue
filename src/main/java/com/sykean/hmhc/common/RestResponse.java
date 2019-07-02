package com.sykean.hmhc.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 返回体
 *
 * @param <T>
 */
@Data
public class RestResponse<T> implements Serializable {
    private int code = 0;
    private T data;
    private String msg;
    private boolean success;

    public RestResponse() {

    }

    public RestResponse(ResponseCode resultCode) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
        this.success = this.code==0;
    }

    public RestResponse(ResponseCode resultCode, boolean success) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
        this.success = success;
    }

    public RestResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.success = this.code==0;
    }

    public RestResponse(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.success = this.code==0;
    }

    public static <T> RestResponse<T> success() {
        RestResponse<T> restResponse = new RestResponse<>();
        restResponse.setCode(0);
        restResponse.setSuccess(true);
        return restResponse;
    }

    public static <T> RestResponse<T> success(T data) {
        RestResponse<T> restResponse = new RestResponse<>();
        restResponse.setCode(0);
        restResponse.setData(data);
        restResponse.setSuccess(true);
        return restResponse;
    }

    public static <T> RestResponse<T> error(ResponseCode responseCode, T data) {
        RestResponse<T> restResponse = new RestResponse<>();
        restResponse.setCode(responseCode.getCode());
        restResponse.setMsg(responseCode.getMsg());
        restResponse.setData(data);
        restResponse.setSuccess(false);
        return restResponse;
    }

    public static <T> RestResponse<T> error(String msg) {
        RestResponse<T> restResponse = new RestResponse<>();
        restResponse.setCode(ResponseCode.ERROR.getCode());
        restResponse.setMsg(msg);
        restResponse.setSuccess(false);
        return restResponse;
    }
}
