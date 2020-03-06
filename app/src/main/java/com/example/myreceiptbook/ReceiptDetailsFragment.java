package com.example.myreceiptbook;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class ReceiptDetailsFragment extends Fragment
{
    private ReceiptDetailsViewModel receiptDetailsViewModel;

    public static ReceiptDetailsFragment newInstance()
    {
        return new ReceiptDetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.receipt_details_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        this.getView().setBackgroundColor(Color.WHITE);

        receiptDetailsViewModel = ViewModelProviders.of(getActivity()).get(ReceiptDetailsViewModel.class);
        receiptDetailsViewModel.getSelectedReceipt().observe(getViewLifecycleOwner(), item ->
        {
            // show title
            // show image
            // show desc short
            // show desc long
        });
    }

    public ReceiptDetailsViewModel receiptDetailsViewModel()
    {
        return receiptDetailsViewModel;
    }
}