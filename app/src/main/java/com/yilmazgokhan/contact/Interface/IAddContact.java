package com.yilmazgokhan.contact.Interface;

import android.content.Context;

import com.yilmazgokhan.contact.HelperClass.ErrorHelper;

import java.util.List;

public interface IAddContact {

    interface View {

        void init();

        void initClicks();

        Context getContext();

        void showSuccessMessage();

        void goToMainActivity();

        void tokenError();

        void showErrorMessages(List<ErrorHelper> errorList);

        void clearErrors();
    }

    interface Presenter {

        void created();

        void getUserToken();

        void addContactButtonClick(String name, String phone, String email, String type);
    }
}
