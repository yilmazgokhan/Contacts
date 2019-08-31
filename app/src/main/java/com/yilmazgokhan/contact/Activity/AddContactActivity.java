package com.yilmazgokhan.contact.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yilmazgokhan.contact.HelperClass.ErrorHelper;
import com.yilmazgokhan.contact.Interface.IAddContact;
import com.yilmazgokhan.contact.Presenter.AddContactPresenter;
import com.yilmazgokhan.contact.R;

import java.util.List;

public class AddContactActivity extends AppCompatActivity  implements IAddContact.View {

    private EditText addContactEditTxtName, addContactEditTxtPhone, addContactEditTxtEmail, addContactEditTxtType;
    private TextView addContactNameError, addContactPhoneError, addContactEmailError, addContactTypeError;
    private Button addContactBtn;
    private AddContactPresenter addContactPresenter;
    private Toolbar addContactToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        addContactPresenter = new AddContactPresenter(this);
        addContactPresenter.created();
    }

    @Override
    public void init() {

        //Toolbar
        addContactToolbar = (Toolbar) findViewById(R.id.addContactToolbar);
        addContactToolbar.setTitle(getResources().getString(R.string.add_new_contact));
        setSupportActionBar(addContactToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        addContactEditTxtName = (EditText) findViewById(R.id.addContactEditTxtName);
        addContactEditTxtPhone = (EditText) findViewById(R.id.addContactEditTxtPhone);
        addContactEditTxtEmail = (EditText) findViewById(R.id.addContactEditTxtEmail);
        addContactEditTxtType = (EditText) findViewById(R.id.addContactEditTxtType);
        addContactNameError = (TextView) findViewById(R.id.addContactNameError);
        addContactPhoneError = (TextView) findViewById(R.id.addContactPhoneError);
        addContactEmailError = (TextView) findViewById(R.id.addContactEmailError);
        addContactTypeError = (TextView) findViewById(R.id.addContactTypeError);
        addContactBtn = (Button) findViewById(R.id.addContactBtn);
    }

    @Override
    public void initClicks() {

        addContactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addContactPresenter.addContactButtonClick(addContactEditTxtName.getText().toString().trim(), addContactEditTxtPhone.getText().toString().trim(),
                        addContactEditTxtEmail.getText().toString().trim(), addContactEditTxtType.getText().toString().trim());

            }
        });
    }

    @Override
    public Context getContext() { return this; }

    @Override
    public void showSuccessMessage() {

        Toast.makeText(this, getString(R.string.contact_created), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void goToMainActivity() {

        this.finish();
    }

    @Override
    public void showErrorMessages(List<ErrorHelper> errorList) {

        for (int i=0; i<errorList.size(); i++) {

            if (errorList.get(i).getParam().equals("name")) {

                addContactNameError.setText(errorList.get(i).getMessage());
                addContactNameError.setVisibility(View.VISIBLE);
            } else if(errorList.get(i).getParam().equals("phone")) {

                addContactPhoneError.setText(errorList.get(i).getMessage());
                addContactPhoneError.setVisibility(View.VISIBLE);
            } else if(errorList.get(i).getParam().equals("email")) {

                addContactEmailError.setText(errorList.get(i).getMessage());
                addContactEmailError.setVisibility(View.VISIBLE);
            } else if(errorList.get(i).getParam().equals("type")) {

                addContactTypeError.setText(errorList.get(i).getMessage());
                addContactTypeError.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void clearErrors() {

        addContactNameError.setVisibility(View.GONE);
        addContactPhoneError.setVisibility(View.GONE);
        addContactEmailError.setVisibility(View.GONE);
        addContactTypeError.setVisibility(View.GONE);
    }

    @Override
    public void tokenError() {

        Toast.makeText(this, getString(R.string.error_verify), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
