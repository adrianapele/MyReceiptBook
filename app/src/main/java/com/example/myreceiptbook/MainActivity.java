package com.example.myreceiptbook;

import android.os.Bundle;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myreceiptbook.model.Receipt;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private MyRecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerViewId);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final ArrayList<Receipt> receipts = new ArrayList<>();
        receipts.add(new Receipt("Title1", "Short Desc1", "", ""));
        receipts.add(new Receipt("Title2", "Short Desc2", "", ""));
        receipts.add(new Receipt("Title3", "Short Desc3", "", ""));
        receipts.add(new Receipt("Title4", "Short Desc4", "", ""));
        receipts.add(new Receipt("Title5", "Short Desc5", "", ""));
        recyclerView.setAdapter(new ReceiptsAdapter(this, receipts));

        RelativeLayout emptyView = findViewById(R.id.emptyViewId);
        recyclerView.setEmptyView(emptyView);
    }
}
