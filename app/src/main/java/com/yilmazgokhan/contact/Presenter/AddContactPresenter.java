package com.yilmazgokhan.contact.Presenter;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.yilmazgokhan.contact.HelperClass.Contact;
import com.yilmazgokhan.contact.HelperClass.JsonParseHelper;
import com.yilmazgokhan.contact.HelperClass.UserCreate;
import com.yilmazgokhan.contact.Interface.IAddContact;
import com.yilmazgokhan.contact.RetrofitApi.ApiClient;
import com.yilmazgokhan.contact.RetrofitApi.IRetrofitApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddContactPresenter implements IAddContact.Presenter{

    private IAddContact.View view;
    private String userToken;

    public AddContactPresenter(IAddContact.View view) {
        this.view = view;
    }

    @Override
    public void created() {

        view.init();
        view.initClicks();
        this.getUserToken();
    }

    @Override
    public void getUserToken() {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(view.getContext());
        userToken = sharedPreferences.getString("apiToken", "");
    }

    @Override
    public void addContactButtonClick(String name, String phone, String email, String type) {

        view.clearErrors();
        IRetrofitApi retrofitApi = ApiClient.getApiClient().create(IRetrofitApi.class);
        UserCreate user = new UserCreate(name, phone, email, type);
        Call<Contact> call = retrofitApi.CreateNewContact(userToken, user);
        call.enqueue(new Callback<Contact>() {
            @Override
            public void onResponse(Call<Contact> call, Response<Contact> response) {

//                view.hideLoading();
                if (response.isSuccessful() && response.body() != null) {
                    view.showSuccessMessage();
                    view.goToMainActivity();
                } else if (response.code() == 401) {

                    view.tokenError();
                } else {

                    try {

                        JsonParseHelper jsonParseHelper = new JsonParseHelper(response.errorBody().string());
                        view.showErrorMessages(jsonParseHelper.getErrorList());

                    } catch (Exception e) {}
                }
            }

            @Override
            public void onFailure(Call<Contact> call, Throwable t) {

//                view.onErrorLoading(t.getLocalizedMessage());
            }
        });

    }
}
