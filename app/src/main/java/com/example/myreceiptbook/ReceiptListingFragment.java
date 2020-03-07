package com.example.myreceiptbook;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myreceiptbook.model.Receipt;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import timber.log.Timber;

public class ReceiptListingFragment extends Fragment implements RecyclerViewClickListener
{
    public static final String LISTING_FRAGMENT_TAG = "listingFragment";

    private ReceiptDetailsViewModel receiptDetailsViewModel;

    public ReceiptListingFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View rootView = inflater.inflate(R.layout.receipt_listing_fragment, container, false);

        MyRecyclerView recyclerView = rootView.findViewById(R.id.recyclerViewId);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        final ArrayList<Receipt> receipts = new ArrayList<>();
        receipts.add(new Receipt("Title1", "Short Desc1", "very very very loooooooooooooooooooooooooooooooooooooooooooooooooooooong description", ""));
        receipts.add(new Receipt("Title2", "Short Desc2", "very loooooooooooooooooooooooooooooooooooooooooooooooooooooong description", ""));
        receipts.add(new Receipt("Title3", "Short Desc3", "very loooooooooooooooooooooooooooooooooooooooooooooooooooooong description", ""));
        receipts.add(new Receipt("Title4", "Short Desc4", "very loooooooooooooooooooooooooooooooooooooooooooooooooooooong description", ""));
        receipts.add(new Receipt("Title5", "Short Desc5", "very very loooooooooooooooooooooooooooooooooooooooooooooooooooooong description", ""));
        recyclerView.setAdapter(new ReceiptsAdapter(getContext(), receipts, this));

        RelativeLayout emptyView = rootView.findViewById(R.id.emptyViewId);
        recyclerView.setEmptyView(emptyView);

        FloatingActionButton fab = rootView.findViewById(R.id.floatingActionBtnId);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(getContext(), "Floating Action Button tapped", Toast.LENGTH_SHORT).show();
            }
        });

        receiptDetailsViewModel = ViewModelProviders.of(getActivity()).get(ReceiptDetailsViewModel.class);

        Timber.i("LISTING FRAGMENT - onCreateView");

        return rootView;
    }

    @Override
    public void onRecyclerViewItemClick(View view, Receipt receipt)
    {
        openFragment(view, receipt);
    }

    private void openFragment(View v, Receipt currentReceipt)
    {
        AppCompatActivity activity = (AppCompatActivity) v.getContext();
        final FragmentManager supportFragmentManager = activity.getSupportFragmentManager();
        Fragment detailsFragment = supportFragmentManager.findFragmentByTag(ReceiptDetailsFragment.DETAILS_FRAGMENT_TAG);

        if (detailsFragment == null)
        {
            detailsFragment = new ReceiptDetailsFragment();
        }

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, detailsFragment, ReceiptDetailsFragment.DETAILS_FRAGMENT_TAG)
                .addToBackStack(ReceiptDetailsFragment.DETAILS_FRAGMENT_TAG)
                .commit();

        receiptDetailsViewModel.selectReceipt(currentReceipt);

        Toast.makeText(getContext(), "Item tapped: " + currentReceipt.getTitle(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Timber.i("LISTING FRAGMENT - onCreate");
    }

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        Timber.i("LISTING FRAGMENT - onAttach");
    }

    @Override
    public void onAttachFragment(@NonNull Fragment childFragment)
    {
        super.onAttachFragment(childFragment);
        Timber.i("LISTING FRAGMENT - onAttachFragment: t%s", childFragment.getTag());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        Timber.i("LISTING FRAGMENT - onActivityCreated");
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Timber.i("LISTING FRAGMENT - onStart");
    }

    @Override
    public void onResume()
    {
        super.onResume();
        Timber.i("LISTING FRAGMENT - onResume");
    }

    @Override
    public void onPause()
    {
        super.onPause();
        Timber.i("LISTING FRAGMENT - onPause");
    }

    @Override
    public void onStop()
    {
        super.onStop();
        Timber.i("LISTING FRAGMENT - onStop");
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        Timber.i("LISTING FRAGMENT - onDestroyView");
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Timber.i("LISTING FRAGMENT - onDestroy");
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        Timber.i("LISTING FRAGMENT - onDetach");
    }
}