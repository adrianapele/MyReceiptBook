package com.example.myreceiptbook;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myreceiptbook.model.Receipt;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ReceiptsAdapter extends MyRecyclerView.Adapter<ReceiptsViewHolder>
{
    private Context context;
    private List<Receipt> receipts;

    ReceiptsAdapter(Context context, List<Receipt> receipts)
    {
        this.context = context;
        this.receipts = receipts;
    }

    @NonNull
    @Override
    public ReceiptsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.receipts_list_row, parent);

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

        holder.itemView.setOnClickListener(v ->
        {
            openFragment(v, currentReceipt);
        });
    }

    private void openFragment(View v, Receipt currentReceipt)
    {
        AppCompatActivity activity = (AppCompatActivity) context;
        final ReceiptDetailsFragment receiptDetailsFragment = ReceiptDetailsFragment.newInstance();
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.realtiveLayoutId, receiptDetailsFragment)
                .addToBackStack("fragment2")
                .commit();

        receiptDetailsFragment.receiptDetailsViewModel().selectRecipt(currentReceipt);

        Toast.makeText(context, "Item tapped: " + currentReceipt.getTitle(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount()
    {
        return receipts.size();
    }
}
