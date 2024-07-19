package com.error;

public enum EmBusinessError implements CommonError{
    //1000 stands for general error type
    PARAMETER_VALIDATION_ERROR(10001,"PARAMETER ILLEGAL"),
    UNKNOWN_ERROR(10002,"UNKNOWN ERROR"),

    //2000 stands for user information relevant error
    USER_NOT_EXIST(20001,"USER DOES NOT EXIST")

    ;
    private int errCode;
    private String errMsg;

    private EmBusinessError(int errCode, String errMsg){
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    @Override
    public int getErrCode() {
        return this.errCode;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }
}
