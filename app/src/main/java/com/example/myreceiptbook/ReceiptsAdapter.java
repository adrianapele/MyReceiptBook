package com.example.myreceiptbook;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myreceiptbook.model.Receipt;
import com.squareup.picasso.Picasso;

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
        view.setOnClickListener((v) ->
        {
            Toast.makeText(context, "Item tapped", Toast.LENGTH_SHORT).show();
        });

        return new ReceiptsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReceiptsViewHolder holder, int position)
    {
        Receipt currentReceipt = receipts.get(position);

        holder.titleTextView.setText(currentReceipt.getTitle());
        holder.shortDescriptionTextView.setText(currentReceipt.getShortDescription());

        Picasso.with(context)
                .load(Uri.parse(currentReceipt.getImageUri()) + ".png")
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(holder.receiptImageView);
    }

    @Override
    public int getItemCount()
    {
        return receipts.size();
    }
}
