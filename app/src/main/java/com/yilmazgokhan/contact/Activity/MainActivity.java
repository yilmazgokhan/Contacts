package com.yilmazgokhan.contact.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.yilmazgokhan.contact.Adapter.MainActivityAdapter;
import com.yilmazgokhan.contact.HelperClass.Contact;
import com.yilmazgokhan.contact.HelperClass.ContactListInfo;
import com.yilmazgokhan.contact.HelperClass.RecyclerViewTouchListener;
import com.yilmazgokhan.contact.Interface.IMain;
import com.yilmazgokhan.contact.Interface.IRecyclerViewClickListener;
import com.yilmazgokhan.contact.Presenter.MainPresenter;
import com.yilmazgokhan.contact.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IMain.View {

    private RecyclerView mainRecyclerView;
    private Toolbar mainToolbar;
    private ImageButton mainButtonAddContact;
    private ProgressBar progressBar;
    private MainPresenter mainPresenter;
    private MainActivityAdapter adapter;
    private List<Contact> contacts;
    private int pageId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainPresenter = new MainPresenter(this);
        mainPresenter.created();
    }

    @Override
    public void init() {

        //Toolbar
        mainToolbar = (Toolbar) findViewById(R.id.mainToolbar);
        mainToolbar.setTitle(getResources().getString(R.string.contacts));
        setSupportActionBar(mainToolbar);

        //SpinKit - ProgressBar
        progressBar = (SpinKitView) findViewById(R.id.mainSpinKit);
        DoubleBounce doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);

        mainRecyclerView = (RecyclerView) findViewById(R.id.mainRecyclerView);
        mainButtonAddContact = (ImageButton) findViewById(R.id.mainButtonAddContact);

        mainRecyclerView = (RecyclerView) findViewById(R.id.mainRecyclerView);
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mainRecyclerView.setItemAnimator(new DefaultItemAnimator());

        contacts = new ArrayList<>();
        adapter = new MainActivityAdapter(this, contacts);
        mainRecyclerView.setAdapter(adapter);
    }

    @Override
    public void initClicks() {

        mainButtonAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mainPresenter.addContactButtonClick();
            }
        });
    }

    @Override
    public void initScrollListener() {

        mainRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1) && newState==RecyclerView.SCROLL_STATE_IDLE) {

                    pageId++;
                    mainPresenter.getContactData(pageId);
                }
            }
        });
    }

    @Override
    public Context getContext() { return this; }

    @Override
    public void onGetResult(ContactListInfo contactListInfo) {

        List<Contact> contactList = contactListInfo.getContacts();
        pageId = contactListInfo.getPage();
        contacts.addAll(contactList);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void initRecyclerClickListener() {

        mainRecyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(this, mainRecyclerView, new IRecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {

                mainPresenter.recyclerViewClick(position);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    @Override
    public void onErrorLoading(String message) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideLoading() {

        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {

        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void goToAddContactActivity() {

        Intent intent = new Intent(this, AddContactActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void goToEditContactActivity(int position) {

        Intent intent = new Intent(this, EditContactActivity.class);

        /**
         * I can send all data this way but I want to try API.
         *
         * intent.putExtra("name", contacts.get(position).getName());
         * intent.putExtra("email", contacts.get(position).getEmail());
         * intent.putExtra("phone", contacts.get(position).getPhone());
         * intent.putExtra("type", contacts.get(position).getType());
         *
         * */
        intent.putExtra("id", contacts.get(position).getId());
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
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.toolbar_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.itemToolbarMenuMainGitHub :
                mainPresenter.gitHubButtonClick();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void goToWebBrowser() {

        Intent browse = new Intent( Intent.ACTION_VIEW , Uri.parse(getResources().getString(R.string.git_hubLink)));
        startActivity(browse);
    }

    @Override
    public void onBackPressed() {

        mainPresenter.onBackPressed();
    }

    @Override
    public void showAppExitInfo() {
        Toast.makeText(this, getResources().getString(R.string.press_again), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void finishApp() { this.finish(); }
}
