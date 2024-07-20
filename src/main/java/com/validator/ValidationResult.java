package com.validator;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class ValidationResult {

//    does validation result has errors
    private boolean hasErrors = false;
//    contains error message
    private Map<String,String> errMsgMap = new HashMap<>();

    public boolean isHasErrors() {
        return hasErrors;
    }

    public void setHasErrors(boolean hasErrors) {
        this.hasErrors = hasErrors;
    }

    public Map<String, String> getErrMsgMap() {
        return errMsgMap;
    }

    public void setErrMsgMap(Map<String, String> errMsgMap) {
        this.errMsgMap = errMsgMap;
    }

//  general method get error result message by format string information
    public String getErrMsg(){
        return StringUtils.join(errMsgMap.values().toArray(), ",");
    }



}
