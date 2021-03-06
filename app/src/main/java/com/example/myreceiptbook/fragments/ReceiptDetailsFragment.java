package com.example.myreceiptbook.fragments;

import android.app.AlertDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myreceiptbook.R;
import com.example.myreceiptbook.model.Receipt;
import com.example.myreceiptbook.model.ReceiptViewModel;
import com.github.chrisbanes.photoview.PhotoView;

public class ReceiptDetailsFragment extends Fragment
{
    public static final String DETAILS_FRAGMENT_TAG = "detailsFragment";

    private ReceiptViewModel receiptViewModel;

    private ImageView imageView;
    private TextView titleTextView;
    private TextView longDescTextView;

    private Button editButton;
    private Button deleteButton;

   public ReceiptDetailsFragment()
   {
   }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        final View rootView = inflater.inflate(R.layout.receipt_details_fragment, container, false);

        titleTextView = rootView.findViewById(R.id.detailsTitleTextId);
        longDescTextView = rootView.findViewById(R.id.detailsLongDescTextId);
        imageView = rootView.findViewById(R.id.detailsImageId);

        editButton = rootView.findViewById(R.id.editButton);
        editButton.setOnClickListener(this::openCreateEditFragment);

        deleteButton = rootView.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(this::openDeleteDialog);

        receiptViewModel = ViewModelProviders.of(getActivity()).get(ReceiptViewModel.class);
        receiptViewModel.getCurrentSelectedReceipt().observe(getViewLifecycleOwner(), new Observer<Receipt>()
        {
            @Override
            public void onChanged(Receipt receipt)
            {
                if (receipt == null)
                    return;

                titleTextView.setText(receipt.getTitle());
                longDescTextView.setText(receipt.getLargeDescription());
                imageView.setImageURI(Uri.parse(receipt.getImageUri()));


//            if (stringImageUri != null && !stringImageUri.isEmpty())
                addImageViewOnClickListener(receipt);
            }
        });

        getActivity().setTitle("Receipt Details");
        return rootView;
    }

    private void openCreateEditFragment(View v)
    {
        AppCompatActivity activity = (AppCompatActivity) v.getContext();
        final FragmentManager supportFragmentManager = activity.getSupportFragmentManager();
        Fragment createEditFragment = supportFragmentManager.findFragmentByTag(CreateEditFragment.CREATE_EDIT_FRAGMENT_TAG);

        if (createEditFragment == null)
        {
            createEditFragment = new CreateEditFragment();
            ((CreateEditFragment) createEditFragment).setRequestType(CreateEditFragment.REQUEST_EDIT);
        }

        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, createEditFragment, CreateEditFragment.CREATE_EDIT_FRAGMENT_TAG)
                .addToBackStack(CreateEditFragment.CREATE_EDIT_FRAGMENT_TAG)
                .commit();
    }

    private void openDeleteDialog(View v)
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setTitle("Delete");
        alertDialogBuilder.setMessage("Do you want to delete this receipt?");
        alertDialogBuilder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        alertDialogBuilder.setPositiveButton("OKAY", (dialog, which) ->
        {
            receiptViewModel.delete(receiptViewModel.getCurrentSelectedReceipt().getValue());
            receiptViewModel.setCurrentSelectedReceipt(null);
            ReceiptDetailsFragment.this.getActivity().onBackPressed();
        });

        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void addImageViewOnClickListener(Receipt receipt)
    {
        imageView.setOnClickListener(view -> openFullSizeImageWithPinch(receipt));
    }

    private void openFullSizeImageWithPinch(Receipt receipt)
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        View imageContainerView = getLayoutInflater().inflate(R.layout.image_zoomable_layout, null);

        PhotoView photoView = imageContainerView.findViewById(R.id.zoomableImageId);
        photoView.setImageURI(Uri.parse(receipt.getImageUri()));

        alertDialogBuilder.setView(imageContainerView);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}