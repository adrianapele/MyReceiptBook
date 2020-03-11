package com.example.myreceiptbook.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.squareup.picasso.Picasso;

import timber.log.Timber;

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
        editButton.setOnClickListener(v -> openCreateEditFragment(v));

        deleteButton = rootView.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(v -> openDeleteDialog(v));

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

                final String stringImageUri = receipt.getImageUri();
                final Uri imageUri = Uri.parse(stringImageUri);
                Picasso.with(getContext())
                        .load(imageUri)
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_background)
                        .into(imageView);

//            if (stringImageUri != null && !stringImageUri.isEmpty())
                addImageViewOnClickListener(receipt);
            }
        });


        Timber.i("DETAILS FRAGMENT - onCreateView");
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
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });

        alertDialogBuilder.setPositiveButton("OKAY", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                receiptViewModel.delete(receiptViewModel.getCurrentSelectedReceipt().getValue());
                receiptViewModel.setCurrentSelectedReceipt(null);
                ReceiptDetailsFragment.this.getActivity().onBackPressed();
            }
        });

        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        Timber.i("DETAILS FRAGMENT - onActivityCreated");
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

    @Override
    public void onAttachFragment(@NonNull Fragment childFragment)
    {
        super.onAttachFragment(childFragment);
        Timber.i("DETAILS FRAGMENT - onAttachFragment: %s", childFragment.getTag());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Timber.i("DETAILS FRAGMENT - onCreate");
    }

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        Timber.i("DETAILS FRAGMENT - onAttach");
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Timber.i("DETAILS FRAGMENT - onStart");
    }

    @Override
    public void onResume()
    {
        super.onResume();
        Timber.i("DETAILS FRAGMENT - onResume");
    }

    @Override
    public void onPause()
    {
        super.onPause();
        Timber.i("DETAILS FRAGMENT - onPause");
    }

    @Override
    public void onStop()
    {
        super.onStop();
        Timber.i("DETAILS FRAGMENT - onStop");
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        Timber.i("DETAILS FRAGMENT - onDestroyView");
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Timber.i("DETAILS FRAGMENT - onDestroy");
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        Timber.i("DETAILS FRAGMENT - onDetach");
    }
}