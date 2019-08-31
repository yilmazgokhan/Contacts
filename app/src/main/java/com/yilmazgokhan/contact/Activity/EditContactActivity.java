package com.yilmazgokhan.contact.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.yilmazgokhan.contact.HelperClass.UserEdit;
import com.yilmazgokhan.contact.Interface.IEditContact;
import com.yilmazgokhan.contact.Presenter.EditContactPresenter;
import com.yilmazgokhan.contact.R;

public class EditContactActivity extends AppCompatActivity implements IEditContact.View {

    private EditText editContactEditTxtName, editContactEditTxtPhone, editContactEditTxtEmail, editContactEditTxtType;
    private EditContactPresenter editContactPresenter;
    private Toolbar editContactToolbar;
    private ProgressBar editContactSpinKit;
    private UserEdit userEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        /**Get Extras**/
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras != null) {
                /*user = new UserEdit(extras.getString("name"), extras.getString("phone"),
                        extras.getString("email"), extras.getString("type"), extras.getString("id"));*/
                userEdit = new UserEdit();
                userEdit.setId(extras.getString("id"));
            }
        }

        editContactPresenter = new EditContactPresenter(this);
        editContactPresenter.created();
    }

    @Override
    public void init() {

        //Toolbar
        editContactToolbar = (Toolbar) findViewById(R.id.editContactToolbar);
        editContactToolbar.setTitle(getResources().getString(R.string.edit_contact));
        setSupportActionBar(editContactToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        //SpinKit - ProgressBar
        editContactSpinKit = (SpinKitView) findViewById(R.id.editContactSpinKit);
        DoubleBounce doubleBounce = new DoubleBounce();
        editContactSpinKit.setIndeterminateDrawable(doubleBounce);

        editContactEditTxtName = (EditText) findViewById(R.id.editContactEditTxtName);
        editContactEditTxtPhone = (EditText) findViewById(R.id.editContactEditTxtPhone);
        editContactEditTxtEmail = (EditText) findViewById(R.id.editContactEditTxtEmail);
        editContactEditTxtType = (EditText) findViewById(R.id.editContactEditTxtType);
    }

    @Override
    public void showLoading() {

        editContactSpinKit.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {

        editContactSpinKit.setVisibility(View.GONE);
    }

    @Override
    public String getUserID() {
        return userEdit.getId();
    }

    @Override
    public void setTexts(UserEdit userEdit) {

        editContactEditTxtName.setText(userEdit.getName());
        editContactEditTxtPhone.setText(userEdit.getPhone());
        editContactEditTxtEmail.setText(userEdit.getEmail());
        editContactEditTxtType.setText(userEdit.getType());
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.toolbar_edit_contact, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                goToMainActivity();
                break;
            case R.id.itemToolbarMenuEditContactDone :
                editContactPresenter.doneButtonClick();
                break;
            case R.id.itemToolbarMenuEditContactDelete :
                editContactPresenter.deleteButtonClick(userEdit.getId());
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public UserEdit getChangedUser() {

        userEdit.setEmail(editContactEditTxtEmail.getText().toString().trim());
        userEdit.setName(editContactEditTxtName.getText().toString().trim());
        userEdit.setPhone(editContactEditTxtPhone.getText().toString().trim());
        userEdit.setType(editContactEditTxtType.getText().toString().trim());
        return userEdit;
    }

    @Override
    public void deleteSuccessful() {
        Toast.makeText(this, getString(R.string.contact_deleted), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateSuccessful() {
        Toast.makeText(this, getString(R.string.contact_updated), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void goToMainActivity() {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void tokenError() {

        Toast.makeText(this, getString(R.string.error_verify), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {

        editContactPresenter.backPressed();
        super.onBackPressed();
    }
}
