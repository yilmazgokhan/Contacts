package com.yilmazgokhan.contact.Presenter;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.yilmazgokhan.contact.HelperClass.ContactListInfo;
import com.yilmazgokhan.contact.Interface.IMain;
import com.yilmazgokhan.contact.RetrofitApi.ApiClient;
import com.yilmazgokhan.contact.RetrofitApi.IRetrofitApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter implements IMain.Presenter{

    private IMain.View view;
    private String userToken;
    private long backPressedTime;

    public MainPresenter(IMain.View view) {
        this.view = view;
    }

    @Override
    public void created() {

        view.init();
        view.showLoading();
        view.initClicks();
        view.initScrollListener();
        this.getUserToken();
        /**For first page**/
        this.getContactData(1);
        view.initRecyclerClickListener();
    }

    @Override
    public void getUserToken() {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(view.getContext());
        userToken = sharedPreferences.getString("apiToken", "");
    }

    @Override
    public void getContactData(int pageId) {

        IRetrofitApi retrofitApi = ApiClient.getApiClient().create(IRetrofitApi.class);
        Call<ContactListInfo> call = retrofitApi.GetUsers(pageId, userToken);
        call.enqueue(new Callback<ContactListInfo>() {
            @Override
            public void onResponse(Call<ContactListInfo> call, Response<ContactListInfo> response) {

                view.hideLoading();
                if (response.isSuccessful() && response.body() != null) {
                    ContactListInfo contactListInfo = response.body();
                    view.onGetResult(contactListInfo);
                } else if (response.code() == 401) {

                    view.hideLoading();
                    view.tokenError();
                }
            }

            @Override
            public void onFailure(Call<ContactListInfo> call, Throwable t) {

                view.hideLoading();
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void recyclerViewClick(int position) {

        view.goToEditContactActivity(position);
    }

    @Override
    public void addContactButtonClick() {

        view.goToAddContactActivity();
    }

    @Override
    public void gitHubButtonClick() {

        view.goToWebBrowser();
    }

    @Override
    public void onBackPressed() {

        if (backPressedTime + 2000 > System.currentTimeMillis()) {

            view.finishApp();
        } else {

            view.showAppExitInfo();
        }
        backPressedTime = System.currentTimeMillis();
    }
}
