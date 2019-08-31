package com.yilmazgokhan.contact.Interface;

import android.content.Context;

import com.yilmazgokhan.contact.HelperClass.ErrorHelper;

import java.util.List;

public interface IRegister {

    interface View {

        void init();

        void initClicks();

        Context getContext();

        void showLoading();

        void hideLoading();

        void showErrorMessages(List<ErrorHelper> errorList);

        void clearErrors();

        void goToMainActivity();

        void goToLoginActivity();

    }

    interface Presenter {

        void created();

        void registerButtonClick(String name, String email, String password);

        void backPressed();

    }
}
