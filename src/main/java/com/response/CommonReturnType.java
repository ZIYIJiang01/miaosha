package com.response;

public class CommonReturnType {
//represents the request's response result is "success" or "fail
    private String status;
//    if status==success, then data return json data that front end needs; if fail, then data use common fi
    private Object data;

//    define a common create method
    public static CommonReturnType create(Object result){
        return CommonReturnType.create(result,"success");
    }

    public static CommonReturnType create(Object result, String status){
        CommonReturnType type = new CommonReturnType();
        type.setStatus(status);
        type.setData(result);
        return type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
