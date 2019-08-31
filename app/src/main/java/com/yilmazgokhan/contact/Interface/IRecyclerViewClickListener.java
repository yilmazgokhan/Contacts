package com.yilmazgokhan.contact.Interface;

import android.view.View;

public interface IRecyclerViewClickListener {

    void onClick(View view, int position);

    void onLongClick(View view, int position);
}
