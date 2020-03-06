package com.example.myreceiptbook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myreceiptbook.model.Receipt;

import java.util.ArrayList;

public class ReceiptListingFragment extends Fragment
{
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
        receipts.add(new Receipt("Title1", "Short Desc1", "", ""));
        receipts.add(new Receipt("Title2", "Short Desc2", "", ""));
        receipts.add(new Receipt("Title3", "Short Desc3", "", ""));
        receipts.add(new Receipt("Title4", "Short Desc4", "", ""));
        receipts.add(new Receipt("Title5", "Short Desc5", "", ""));
        recyclerView.setAdapter(new ReceiptsAdapter(getContext(), receipts));

        RelativeLayout emptyView = rootView.findViewById(R.id.emptyViewId);
        recyclerView.setEmptyView(emptyView);

        return rootView;
    }
}
