package com.yilmazgokhan.contact.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.yilmazgokhan.contact.HelperClass.Contact;
import com.yilmazgokhan.contact.R;

import java.util.List;

public class MainActivityAdapter extends RecyclerView.Adapter<MainActivityAdapter.RecyclerViewAdapter> {

    private Context context;
    private List<Contact> contacts;

    public MainActivityAdapter(Context context, List<Contact> users) {
        this.context = context;
        this.contacts = users;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_main, parent, false);
        return new RecyclerViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter holder, int position) {

        Contact user = contacts.get(position);
//        holder.rowTxtCount.setText(String.valueOf(position));
        holder.rowTxtName.setText(user.getName());
        holder.rowTxtEmail.setText(user.getEmail());
        holder.rowTxtType.setText(user.getType());
        holder.rowTxtDate.setText(user.getDate());

        char firstLetter = user.getName().toUpperCase().charAt(0);
        holder.rowTxtCount.setText(String.valueOf(firstLetter));

    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    class RecyclerViewAdapter extends RecyclerView.ViewHolder {

        TextView rowTxtCount, rowTxtName, rowTxtEmail, rowTxtType, rowTxtDate;

        RecyclerViewAdapter(@NonNull View itemView) {
            super(itemView);
            rowTxtCount = (TextView) itemView.findViewById(R.id.rowTxtCount);
            rowTxtName = (TextView) itemView.findViewById(R.id.rowTxtName);
            rowTxtEmail = (TextView) itemView.findViewById(R.id.rowTxtEmail);
            rowTxtType = (TextView) itemView.findViewById(R.id.rowTxtType);
            rowTxtDate = (TextView) itemView.findViewById(R.id.rowTxtDate);

        }
    }
}