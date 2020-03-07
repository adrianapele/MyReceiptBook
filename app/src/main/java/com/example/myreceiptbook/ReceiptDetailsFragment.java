package com.example.myreceiptbook;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import timber.log.Timber;

public class ReceiptDetailsFragment extends Fragment
{
    public static final String DETAILS_FRAGMENT_TAG = "detailsFragment";

    private ReceiptDetailsViewModel receiptDetailsViewModel;

    private SubsamplingScaleImageView imageView;
    private TextView titleTextView;
    private TextView longDescTextView;

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

//        imageView.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
//                View mView = getLayoutInflater().inflate(R.layout.image_zoomable_layout, null);
//                PhotoView photoView = mView.findViewById(R.id.imageView);
//                photoView.setImageResource(R.drawable.ic_launcher_background);
//                mBuilder.setView(mView);
//                AlertDialog mDialog = mBuilder.create();
//                mDialog.show();
//            }
//        });

        Timber.i("DETAILS FRAGMENT - onCreateView");
        return rootView;
    }

    @Override
    public void onAttachFragment(@NonNull Fragment childFragment)
    {
        super.onAttachFragment(childFragment);
        Timber.i("DETAILS FRAGMENT - onAttachFragment: %s", childFragment.getTag());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        receiptDetailsViewModel = ViewModelProviders.of(getActivity()).get(ReceiptDetailsViewModel.class);
        receiptDetailsViewModel.getSelectedReceipt().observe(getViewLifecycleOwner(), receipt ->
        {
            longDescTextView.setText(receipt.getLargeDescription());
            titleTextView.setText(receipt.getTitle());
            imageView.setImage(ImageSource.resource(R.drawable.ic_launcher_foreground));
//            Picasso.with(getContext())
//                    .load(Uri.parse(receipt.getImageUri()) + ".png")
//                    .placeholder(R.drawable.ic_launcher_background)
//                    .error(R.drawable.ic_launcher_background)
//                    .into(imageView);
        });

        Timber.i("DETAILS FRAGMENT - onActivityCreated");
    }

    public ReceiptDetailsViewModel receiptDetailsViewModel()
    {
        return receiptDetailsViewModel;
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