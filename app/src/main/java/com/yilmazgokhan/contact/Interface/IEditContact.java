package com.yilmazgokhan.contact.Interface;

import android.content.Context;

import com.yilmazgokhan.contact.HelperClass.Contact;

public interface IEditContact {

    interface View {

        void init();

        Context getContext();

        String getUserID();

        void setTexts(Contact user);

        Contact getChangedUser();

        void deleteSuccessful();

        void goToMainActivity();

        void tokenError();

        void updateSuccessful();

        void showLoading();

        void hideLoading();
    }

    interface Presenter {

        void created();

        void getUserToken();

        void getUserData();

        void deleteButtonClick(String id);

        void doneButtonClick();

        void backPressed();
    }
}
