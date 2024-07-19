package com.error;

//decorator pattern to realize the business exception
public class BusinessException extends Exception implements CommonError{

    private CommonError commonError;

    //directly receive EmBusinessError parameter to construct exception
    public BusinessException(CommonError commonError){
        super();
        this.commonError = commonError;
    }
//  receive errMsg define by ourselves to contruct exception
    public BusinessException(CommonError commonError, String errMsg){
        super();
        this.commonError = commonError;
        this.commonError.setErrMsg(errMsg);
    }

    @Override
    public int getErrCode() {
        return this.commonError.getErrCode();
    }

    @Override
    public String getErrMsg() {
        return this.commonError.getErrMsg();
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.commonError.setErrMsg(errMsg);
        return this;
    }
}
