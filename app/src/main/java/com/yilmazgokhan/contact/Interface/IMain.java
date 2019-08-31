package com.yilmazgokhan.contact.Interface;

import android.content.Context;

import com.yilmazgokhan.contact.HelperClass.ContactListInfo;


public interface IMain {

    interface View {

        void init();

        void initClicks();

        void initScrollListener();

        void onGetResult(final ContactListInfo contactListInfo);

        void onErrorLoading(String message);

        void initRecyclerClickListener();

        Context getContext();

        void hideLoading();

        void showLoading();

        void goToAddContactActivity();

        void goToEditContactActivity(int position);

        void tokenError();

        void goToWebBrowser();

        void finishApp();

        void showAppExitInfo();
    }

    interface Presenter {

        void created();

        void getUserToken();

        void getContactData(int pageId);

        void recyclerViewClick(int position);

        void addContactButtonClick();

        void gitHubButtonClick();

        void onBackPressed();
    }
}
