package com.yilmazgokhan.contact.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.yilmazgokhan.contact.HelperClass.ErrorHelper;
import com.yilmazgokhan.contact.Interface.IRegister;
import com.yilmazgokhan.contact.Presenter.RegisterPresenter;
import com.yilmazgokhan.contact.R;

import java.util.List;

public class RegisterActivity extends AppCompatActivity  implements IRegister.View {

    EditText registerEditTxtName, registerEditTxtEmail, registerEditTxtPassword;
    TextView registerTxtNameError, registerTxtEmailError, registerTxtPasswordError;
    Button registerBtnRegister;
    private ProgressBar registerSpinKit;
    RegisterPresenter registerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerPresenter = new RegisterPresenter(this);
        registerPresenter.created();
    }

    @Override
    public void init() {

        //SpinKit - ProgressBar
        registerSpinKit = (SpinKitView) findViewById(R.id.registerSpinKit);
        DoubleBounce doubleBounce = new DoubleBounce();
        registerSpinKit.setIndeterminateDrawable(doubleBounce);

        registerEditTxtName = (EditText) findViewById(R.id.registerEditTxtName);
        registerEditTxtEmail = (EditText) findViewById(R.id.registerEditTxtEmail);
        registerEditTxtPassword = (EditText) findViewById(R.id.registerEditTxtPassword);
        registerTxtNameError = (TextView) findViewById(R.id.registerTxtNameError);
        registerTxtEmailError = (TextView) findViewById(R.id.registerTxtEmailError);
        registerTxtPasswordError = (TextView) findViewById(R.id.registerTxtPasswordError);
        registerBtnRegister = (Button) findViewById(R.id.registerBtnRegister);
    }

    @Override
    public void initClicks() {

        registerBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                registerPresenter.registerButtonClick(registerEditTxtName.getText().toString().trim(),
                        registerEditTxtEmail.getText().toString().trim(), registerEditTxtPassword.getText().toString().trim());
            }
        });
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showLoading() {

        registerSpinKit.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {

        registerSpinKit.setVisibility(View.GONE);
    }

    @Override
    public void showErrorMessages(List<ErrorHelper> errorList) {

        for (int i=0; i<errorList.size(); i++) {

            if (errorList.get(i).getParam().equals("name")) {

                registerTxtNameError.setText(errorList.get(i).getMessage());
                registerTxtNameError.setVisibility(View.VISIBLE);
            } else if(errorList.get(i).getParam().equals("email")) {

                registerTxtEmailError.setText(errorList.get(i).getMessage());
                registerTxtEmailError.setVisibility(View.VISIBLE);
            } else if(errorList.get(i).getParam().equals("password")) {

                registerTxtPasswordError.setText(errorList.get(i).getMessage());
                registerTxtPasswordError.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void clearErrors() {

        registerTxtNameError.setVisibility(View.GONE);
        registerTxtEmailError.setVisibility(View.GONE);
        registerTxtPasswordError.setVisibility(View.GONE);
    }

    @Override
    public void goToMainActivity() {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void goToLoginActivity() {

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {

        registerPresenter.backPressed();
        super.onBackPressed();
    }
}
