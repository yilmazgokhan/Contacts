package com.yilmazgokhan.contact.Presenter;

import com.yilmazgokhan.contact.HelperClass.ApiToken;
import com.yilmazgokhan.contact.HelperClass.JsonParseHelper;
import com.yilmazgokhan.contact.HelperClass.User;
import com.yilmazgokhan.contact.Interface.ILogin;
import com.yilmazgokhan.contact.RetrofitApi.ApiClient;
import com.yilmazgokhan.contact.RetrofitApi.IRetrofitApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter implements ILogin.Presenter{

    private ILogin.View view;

    public LoginPresenter(ILogin.View view) {

        this.view = view;
    }

    @Override
    public void created() {

        view.init();
        view.initClicks();
    }

    @Override
    public void loginButtonClick(String email, String password) {

        view.showLoading();
        view.clearErrors();
        IRetrofitApi retrofitApi = ApiClient.getApiClient().create(IRetrofitApi.class);
        User user = new User(email, password);
        Call<ApiToken> call = retrofitApi.Login(user);
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
    public void registerButtonClick() {

        view.goToRegisterActivity();
    }

    @Override
    public void githubLinkClick() {

        view.goToWebBrowser(0);
    }

    @Override
    public void privacyPolicyLinkClick() {

        view.goToWebBrowser(1);
    }
}
