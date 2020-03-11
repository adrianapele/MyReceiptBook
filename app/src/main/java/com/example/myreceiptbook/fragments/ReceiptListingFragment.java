package com.example.myreceiptbook.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

import com.example.myreceiptbook.adapter.MyRecyclerView;
import com.example.myreceiptbook.R;
import com.example.myreceiptbook.adapter.ReceiptAdapter;
import com.example.myreceiptbook.model.Receipt;
import com.example.myreceiptbook.model.ReceiptViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import timber.log.Timber;

public class ReceiptListingFragment extends Fragment implements ReceiptAdapter.RecyclerViewClickListener
{
    public static final String LISTING_FRAGMENT_TAG = "listingFragment";

    private ReceiptViewModel receiptViewModel;

    public ReceiptListingFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View rootView = inflater.inflate(R.layout.receipt_listing_fragment, container, false);

        MyRecyclerView recyclerView = rootView.findViewById(R.id.recyclerViewId);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        RelativeLayout emptyView = rootView.findViewById(R.id.emptyViewId);
        recyclerView.setEmptyView(emptyView);

        final ReceiptAdapter adapter = new ReceiptAdapter();
        adapter.setOnRecyclerViewItemClickListener(this);
        recyclerView.setAdapter(adapter);

        receiptViewModel = ViewModelProviders.of(getActivity()).get(ReceiptViewModel.class);
        receiptViewModel.getAllNotes().observe(getViewLifecycleOwner(), receipts ->
                adapter.submitList(receipts));

        FloatingActionButton fab = rootView.findViewById(R.id.floatingActionBtnId);
        fab.setOnClickListener(view ->
        {
            Toast.makeText(getContext(), "Floating Action Button tapped", Toast.LENGTH_SHORT).show();
            openCreateEditFragment(view);
        });

        Timber.i("LISTING FRAGMENT - onCreateView");
        return rootView;
    }

    private void openCreateEditFragment(View view)
    {
        AppCompatActivity activity = (AppCompatActivity) view.getContext();
        final FragmentManager supportFragmentManager = activity.getSupportFragmentManager();
        Fragment createEditFragment = supportFragmentManager.findFragmentByTag(CreateEditFragment.CREATE_EDIT_FRAGMENT_TAG);

        if (createEditFragment == null)
        {
            createEditFragment = new CreateEditFragment();
            ((CreateEditFragment) createEditFragment).setRequestType(CreateEditFragment.REQUEST_ADD);
        }

        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, createEditFragment, CreateEditFragment.CREATE_EDIT_FRAGMENT_TAG)
                .addToBackStack(CreateEditFragment.CREATE_EDIT_FRAGMENT_TAG)
                .commit();
        receiptViewModel.setCurrentSelectedReceipt(null);
    }

    @Override
    public void onRecyclerViewItemClick(View view, Receipt receipt)
    {
        openDetailsFragment(view, receipt);
    }

    private void openDetailsFragment(View v, Receipt currentReceipt)
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

        receiptViewModel.setCurrentSelectedReceipt(currentReceipt);

        Toast.makeText(getContext(), "Item tapped: " + currentReceipt.getTitle(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater)
    {
        inflater.inflate(R.menu.delete_all_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.delete_all_receipts:
                if (receiptViewModel.getAllNotes().getValue().size() == 0)
                    Toast.makeText(getContext(), "You don't have receipts to delete", Toast.LENGTH_SHORT).show();
                else
                {
                    receiptViewModel.deleteAllNotes();
                    Toast.makeText(getContext(), "All receipts deleted", Toast.LENGTH_SHORT).show();
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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