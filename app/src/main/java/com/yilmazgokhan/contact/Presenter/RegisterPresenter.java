package com.yilmazgokhan.contact.Presenter;

import com.yilmazgokhan.contact.HelperClass.ApiToken;
import com.yilmazgokhan.contact.HelperClass.JsonParseHelper;
import com.yilmazgokhan.contact.HelperClass.User;
import com.yilmazgokhan.contact.Interface.IRegister;
import com.yilmazgokhan.contact.RetrofitApi.ApiClient;
import com.yilmazgokhan.contact.RetrofitApi.IRetrofitApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterPresenter implements IRegister.Presenter {

    IRegister.View view;

    public RegisterPresenter(IRegister.View view) {
        this.view = view;
    }

    @Override
    public void created() {

        view.init();
        view.initClicks();
    }

    @Override
    public void registerButtonClick(String name, String email, String password) {

        view.showLoading();
        view.clearErrors();
        IRetrofitApi retrofitApi = ApiClient.getApiClient().create(IRetrofitApi.class);
        User user = new User(email, name, password);
        Call<ApiToken> call = retrofitApi.Register(user);
        call.enqueue(new Callback<ApiToken>() {
            @Override
            public void onResponse(Call<ApiToken> call, Response<ApiToken> response) {

                ApiToken apiToken = response.body();
                if (response.isSuccessful() && response.body() != null) {
                    apiToken.setSharedPreferences(view.getContext());
                    view.goToMainActivity();
                } else if (response.errorBody() != null) {

                    try {

                        view.hideLoading();
                        JsonParseHelper jsonParseHelper = new JsonParseHelper(response.errorBody().string());
                        view.showErrorMessages(jsonParseHelper.getErrorList());

                    } catch (Exception e) {}
                }
            }

            @Override
            public void onFailure(Call<ApiToken> call, Throwable t) {

                view.hideLoading();
            }
        });
    }

    @Override
    public void backPressed() {

        view.goToLoginActivity();
    }
}
