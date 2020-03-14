package com.example.myreceiptbook.fragments;

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

import com.example.myreceiptbook.R;
import com.example.myreceiptbook.adapter.MyRecyclerView;
import com.example.myreceiptbook.adapter.ReceiptAdapter;
import com.example.myreceiptbook.model.Receipt;
import com.example.myreceiptbook.model.ReceiptViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

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
        receiptViewModel.getAllNotes().observe(getViewLifecycleOwner(), adapter::submitList);

        FloatingActionButton fab = rootView.findViewById(R.id.floatingActionBtnId);
        fab.setOnClickListener(this::openCreateEditFragment);

        getActivity().setTitle("My Receipts");
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
            detailsFragment = new ReceiptDetailsFragment();

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, detailsFragment, ReceiptDetailsFragment.DETAILS_FRAGMENT_TAG)
                .addToBackStack(ReceiptDetailsFragment.DETAILS_FRAGMENT_TAG)
                .commit();

        receiptViewModel.setCurrentSelectedReceipt(currentReceipt);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater)
    {
        inflater.inflate(R.menu.delete_all_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if (item.getItemId() == R.id.delete_all_receipts)
        {
            final List<Receipt> receipts = receiptViewModel.getAllNotes().getValue();
            if (receipts != null && receipts.size() == 0)
                Toast.makeText(getContext(), "You don't have receipts to delete", Toast.LENGTH_SHORT).show();
            else {
                receiptViewModel.deleteAllNotes();
                Toast.makeText(getContext(), "All receipts deleted", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}