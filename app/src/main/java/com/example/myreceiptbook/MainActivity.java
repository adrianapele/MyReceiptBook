package com.example.myreceiptbook;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myreceiptbook.fragments.ReceiptListingFragment;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, new ReceiptListingFragment(), ReceiptListingFragment.LISTING_FRAGMENT_TAG)
                .commitNow();
    }
}