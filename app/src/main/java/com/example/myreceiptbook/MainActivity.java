package com.example.myreceiptbook;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Timber.i("MAIN ACTIVITY - before super.onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, new ReceiptListingFragment(), ReceiptListingFragment.LISTING_FRAGMENT_TAG)
                .commitNow();

        Timber.i("MAIN ACTIVITY - onCreate");
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        Timber.i("MAIN ACTIVITY - onStart");
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Timber.i("MAIN ACTIVITY - onResume");
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        Timber.i("MAIN ACTIVITY - onPause");
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Timber.i("MAIN ACTIVITY - onDestroy");
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        Timber.i("MAIN ACTIVITY - onRestart");
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        Timber.i("MAIN ACTIVITY - onStop");
    }

    @Override
    public void onAttachFragment(@NonNull Fragment fragment)
    {
        super.onAttachFragment(fragment);

        Timber.i("MAIN ACTIVITY - onAttachFragment: %s", fragment.getClass());
        Timber.i("MAIN ACTIVITY - onAttachFragment: %s", fragment.getTag());
    }

//    @Override
//    public void onBackPressed()
//    {
//        if (getSupportFragmentManager().getBackStackEntryCount() > 0)
//            getSupportFragmentManager().popBackStack();
//        else
//            finish();
//    }
}