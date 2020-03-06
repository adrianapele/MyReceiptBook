package com.example.myreceiptbook;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.squareup.picasso.Picasso;

public class ReceiptDetailsFragment extends Fragment
{
    private ReceiptDetailsViewModel receiptDetailsViewModel;

    private ImageView imageView;
    private TextView titleTextView;
    private TextView longDescTextView;

    public static ReceiptDetailsFragment newInstance()
    {
        return new ReceiptDetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        final View rootView = inflater.inflate(R.layout.receipt_details_fragment, container, false);

        imageView = rootView.findViewById(R.id.image);
        titleTextView = rootView.findViewById(R.id.title);
        longDescTextView = rootView.findViewById(R.id.longDesc);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        this.getView().setBackgroundColor(Color.WHITE);

        receiptDetailsViewModel = ViewModelProviders.of(getActivity()).get(ReceiptDetailsViewModel.class);
        receiptDetailsViewModel.getSelectedReceipt().observe(getViewLifecycleOwner(), receipt ->
        {
            longDescTextView.setText(receipt.getLargeDescription());
            titleTextView.setText(receipt.getTitle());

            Picasso.with(getContext())
                    .load(Uri.parse(receipt.getImageUri()) + ".png")
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(imageView);
        });
    }

    public ReceiptDetailsViewModel receiptDetailsViewModel()
    {
        return receiptDetailsViewModel;
    }
}