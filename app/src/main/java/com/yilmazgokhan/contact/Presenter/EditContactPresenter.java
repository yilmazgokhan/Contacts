package com.yilmazgokhan.contact.Presenter;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.yilmazgokhan.contact.HelperClass.ApiAnswer;
import com.yilmazgokhan.contact.HelperClass.UserEdit;
import com.yilmazgokhan.contact.Interface.IEditContact;
import com.yilmazgokhan.contact.RetrofitApi.ApiClient;
import com.yilmazgokhan.contact.RetrofitApi.IRetrofitApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditContactPresenter implements IEditContact.Presenter {

    private IEditContact.View view;
    private String userToken;

    public EditContactPresenter(IEditContact.View view) { this.view = view; }

    @Override
    public void created() {

        view.init();
        this.getUserToken();
        this.getUserData();
    }

    @Override
    public void getUserToken() {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(view.getContext());
        userToken = sharedPreferences.getString("apiToken", "");
    }

    @Override
    public void getUserData() {

        view.showLoading();
        String userId = view.getUserID();
        IRetrofitApi retrofitApi = ApiClient.getApiClient().create(IRetrofitApi.class);
        Call<UserEdit> call = retrofitApi.GetTheUser(userId, userToken);
        call.enqueue(new Callback<UserEdit>() {
            @Override
            public void onResponse(Call<UserEdit> call, Response<UserEdit> response) {

                view.hideLoading();
                if (response.isSuccessful() && response.body() != null) {
                    UserEdit userEdit = response.body();
                    view.setTexts(userEdit);

                } else if (response.code() == 401) {

                    view.hideLoading();
                    view.tokenError();
                }
            }

            @Override
            public void onFailure(Call<UserEdit> call, Throwable t) {

                view.hideLoading();
            }
        });

    }

    @Override
    public void deleteButtonClick(String id) {

        IRetrofitApi retrofitApi = ApiClient.getApiClient().create(IRetrofitApi.class);
        Call<ApiAnswer> call = retrofitApi.DeleteContact(id, userToken);
        call.enqueue(new Callback<ApiAnswer>() {
            @Override
            public void onResponse(Call<ApiAnswer> call, Response<ApiAnswer> response) {

                if (response.code() == 200) {

                    view.deleteSuccessful();
                    view.goToMainActivity();
                } else if (response.code() == 401) {

                    view.tokenError();
                }
            }

            @Override
            public void onFailure(Call<ApiAnswer> call, Throwable t) {

            }
        });
    }

    @Override
    public void doneButtonClick() {

        UserEdit user = view.getChangedUser();
        IRetrofitApi retrofitApi = ApiClient.getApiClient().create(IRetrofitApi.class);
        Call<ApiAnswer> call = retrofitApi.UpdateContact(user.getId(), userToken, user);
        call.enqueue(new Callback<ApiAnswer>() {
            @Override
            public void onResponse(Call<ApiAnswer> call, Response<ApiAnswer> response) {

                if (response.code() == 200) {

                    view.updateSuccessful();
                    view.goToMainActivity();
                } else if (response.code() == 401) {

                    view.tokenError();
                }
            }

            @Override
            public void onFailure(Call<ApiAnswer> call, Throwable t) {

            }
        });
    }

    @Override
    public void backPressed() {

        view.goToMainActivity();
    }
}
