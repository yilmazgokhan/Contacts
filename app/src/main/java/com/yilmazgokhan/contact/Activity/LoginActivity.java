package com.yilmazgokhan.contact.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.yilmazgokhan.contact.Interface.ILogin;
import com.yilmazgokhan.contact.Presenter.LoginPresenter;
import com.yilmazgokhan.contact.R;

import java.util.List;

public class LoginActivity extends AppCompatActivity implements ILogin.View {

    private EditText loginEditTxtEmail, loginEditTxtPassword;
    private TextView loginTxtEmailError, loginTxtPasswordError, loginTxtGitHub, loginTxtPrivacyPolicy;
    private Button loginBtnLogin, loginBtnRegister;
    private ProgressBar loginSpinKit;
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginPresenter = new LoginPresenter(this);
        loginPresenter.created();
    }

    @Override
    public void init() {

        //SpinKit - ProgressBar
        loginSpinKit = (SpinKitView) findViewById(R.id.loginSpinKit);
        DoubleBounce doubleBounce = new DoubleBounce();
        loginSpinKit.setIndeterminateDrawable(doubleBounce);

        loginEditTxtEmail = (EditText) findViewById(R.id.loginEditTxtEmail);
        loginEditTxtPassword = (EditText) findViewById(R.id.loginEditTxtPassword);
        loginTxtEmailError = (TextView) findViewById(R.id.loginTxtEmailError);
        loginTxtPasswordError = (TextView) findViewById(R.id.loginTxtPasswordError);
        loginTxtGitHub = (TextView) findViewById(R.id.loginTxtGitHub);
        loginTxtPrivacyPolicy = (TextView) findViewById(R.id.loginTxtPrivacyPolicy);
        loginBtnLogin = (Button) findViewById(R.id.loginBtnLogin);
        loginBtnRegister = (Button) findViewById(R.id.loginBtnRegister);
    }

    @Override
    public void initClicks() {

        loginBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginPresenter.loginButtonClick(loginEditTxtEmail.getText().toString().trim(), loginEditTxtPassword.getText().toString().trim());
            }
        });

        loginBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginPresenter.registerButtonClick();
            }
        });

        loginTxtGitHub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginPresenter.githubLinkClick();
            }
        });

        loginTxtPrivacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginPresenter.privacyPolicyLinkClick();
            }
        });
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public void goToRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showLoading() {

        loginSpinKit.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {

        loginSpinKit.setVisibility(View.GONE);
    }

    @Override
    public void showErrorMessages(List<ErrorHelper> errorList) {

        for (int i=0; i<errorList.size(); i++) {

            if (errorList.get(i).getParam().equals("email")) {

                loginTxtEmailError.setText(errorList.get(i).getMessage());
                loginTxtEmailError.setVisibility(View.VISIBLE);
            } else if(errorList.get(i).getParam().equals("password")) {

                loginTxtPasswordError.setText(errorList.get(i).getMessage());
                loginTxtPasswordError.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void clearErrors() {
        loginTxtEmailError.setVisibility(View.GONE);
        loginTxtPasswordError.setVisibility(View.GONE);
    }

    @Override
    public void goToMainActivity() {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void goToWebBrowser(int id) {

        if (id == 0) {

            Intent browse = new Intent( Intent.ACTION_VIEW , Uri.parse(getResources().getString(R.string.git_hubLink)));
            startActivity(browse);
        } else {

            Intent browse = new Intent( Intent.ACTION_VIEW , Uri.parse(getResources().getString(R.string.privacyPolicyLink)));
            startActivity(browse);
        }
    }
}
