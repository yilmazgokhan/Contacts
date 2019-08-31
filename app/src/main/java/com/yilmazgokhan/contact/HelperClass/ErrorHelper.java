package com.yilmazgokhan.contact.HelperClass;

public class ErrorHelper {

    /*
    * API can send error like that:
    * {
    *   "errors": [
    *   {
    *      "msg": "Please enter a valid email",
    *     "param": "email",
    *    "location": "body"
    *   }]
    * }
    */
    String message;
    String param;
    String location;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
