package com.yilmazgokhan.contact.Interface;

import android.content.Context;

import com.yilmazgokhan.contact.HelperClass.ErrorHelper;

import java.util.List;

public interface ILogin {

    interface View {

        void init();

        void initClicks();

        void goToRegisterActivity();

        Context getContext();

        void showLoading();

        void hideLoading();

        void showErrorMessages(List<ErrorHelper> errorList);

        void clearErrors();

        void goToMainActivity();

        void goToWebBrowser(int id);
    }

    interface Presenter {

        void created();

        void loginButtonClick(String username, String password);

        void registerButtonClick();

        void githubLinkClick();

        void privacyPolicyLinkClick();
    }
}
