package com.example.myreceiptbook;

import android.view.View;

import com.example.myreceiptbook.model.Receipt;

public interface RecyclerViewClickListener
{
    void onRecyclerViewItemClick(View view, Receipt receipt);
}
