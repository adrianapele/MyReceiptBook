package com.example.myreceiptbook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myreceiptbook.model.Receipt;

import java.util.ArrayList;

public class ReceiptListingFragment extends Fragment implements RecyclerViewClickListener
{
    private ReceiptDetailsViewModel receiptDetailsViewModel;

    public ReceiptListingFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View rootView = inflater.inflate(R.layout.fragment_receipt_listing, container, false);

        MyRecyclerView recyclerView = rootView.findViewById(R.id.recyclerViewId);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        final ArrayList<Receipt> receipts = new ArrayList<>();
        receipts.add(new Receipt("Title1", "Short Desc1", "very loooooooooooooooooooooooooooooooooooooooooooooooooooooong description", ""));
        receipts.add(new Receipt("Title2", "Short Desc2", "very loooooooooooooooooooooooooooooooooooooooooooooooooooooong description", ""));
        receipts.add(new Receipt("Title3", "Short Desc3", "very loooooooooooooooooooooooooooooooooooooooooooooooooooooong description", ""));
        receipts.add(new Receipt("Title4", "Short Desc4", "very loooooooooooooooooooooooooooooooooooooooooooooooooooooong description", ""));
        receipts.add(new Receipt("Title5", "Short Desc5", "very loooooooooooooooooooooooooooooooooooooooooooooooooooooong description", ""));
        recyclerView.setAdapter(new ReceiptsAdapter(getContext(), receipts, this));

        RelativeLayout emptyView = rootView.findViewById(R.id.emptyViewId);
        recyclerView.setEmptyView(emptyView);

        receiptDetailsViewModel = ViewModelProviders.of(getActivity()).get(ReceiptDetailsViewModel.class);

        return rootView;
    }

    @Override
    public void onRecyclerViewItemClick(View view, Receipt recipt)
    {
        openFragment(view, recipt);
    }

    private void openFragment(View v, Receipt currentReceipt)
    {
        AppCompatActivity activity = (AppCompatActivity) getContext();
        final ReceiptDetailsFragment receiptDetailsFragment = ReceiptDetailsFragment.newInstance();
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.realtiveLayoutId, receiptDetailsFragment)
                .addToBackStack("fragment1")
                .commit();

//        receiptDetailsFragment.receiptDetailsViewModel().selectRecipt(currentReceipt);

        receiptDetailsViewModel.selectRecipt(currentReceipt);

        Toast.makeText(getContext(), "Item tapped: " + currentReceipt.getTitle(), Toast.LENGTH_SHORT).show();
    }
}
