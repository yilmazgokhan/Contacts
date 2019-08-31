package com.yilmazgokhan.contact.HelperClass;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.annotations.SerializedName;

public class ApiToken {

    @SerializedName("token")
    private String token;

    public String getToken() {
        return token;
    }

    public void setSharedPreferences(Context context) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putString("apiToken", this.token).apply();
    }
}
