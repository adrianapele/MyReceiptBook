package com.example.myreceiptbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myreceiptbook.model.Receipt;

import java.util.List;

public class ReceiptsAdapter extends RecyclerView.Adapter<ReceiptsViewHolder>
{
    private Context context;
    private List<Receipt> receipts;

    public ReceiptsAdapter(Context context, List<Receipt> receipts)
    {
        this.context = context;
        this.receipts = receipts;
    }

    @NonNull
    @Override
    public ReceiptsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.receipts_list_row, null);

        return new ReceiptsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReceiptsViewHolder holder, int position)
    {
        Receipt currentReceipt = receipts.get(position);

        holder.titleTextView.setText(currentReceipt.getTitle());
        holder.shortDescriptionTextView.setText(currentReceipt.getShortDescription());
        // set image view
    }

    @Override
    public int getItemCount()
    {
        return receipts.size();
    }
}
