package com.yilmazgokhan.contact.HelperClass;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonParseHelper {

    String errorBody;
    List<ErrorHelper> errorList;

    public JsonParseHelper(String errorBody) {
        this.errorBody = errorBody;
        errorList = new ArrayList<>();
    }

    public List<ErrorHelper> getErrorList() {

        try {
            JSONObject jsonObject = new JSONObject(errorBody);
            JSONArray jsonArray = jsonObject.getJSONArray("errors");
            for (int i=0; i<jsonArray.length(); i++) {

                JSONObject error = jsonArray.getJSONObject(i);
                ErrorHelper errorHelper = new ErrorHelper();
                errorHelper.setMessage(error.get("msg").toString());
                errorHelper.setParam(error.get("param").toString());
                errorHelper.setLocation(error.get("location").toString());
                errorList.add(errorHelper);
            }
        } catch (Exception e) {}

        return errorList;
    }
}
