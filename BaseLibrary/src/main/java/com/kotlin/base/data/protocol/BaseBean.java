package com.kotlin.base.data.protocol;

import com.kotlin.base.common.ResultCode;

import java.io.Serializable;

public class BaseBean implements Serializable {


    private boolean result;
    private String message;
    private int code;


    /**
     * API是否请求失败
     * @return 失败返回true, 成功返回false
     */
    public boolean isCodeInvalid() {
        return code != ResultCode.SUCCESS;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
