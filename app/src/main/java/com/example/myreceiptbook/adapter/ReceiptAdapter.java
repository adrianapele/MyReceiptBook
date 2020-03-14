package com.example.myreceiptbook.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myreceiptbook.R;
import com.example.myreceiptbook.model.Receipt;

public class ReceiptAdapter extends ListAdapter<Receipt, ReceiptAdapter.ReceiptsViewHolder>
{
    private RecyclerViewClickListener listener;

    public ReceiptAdapter()
    {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Receipt> DIFF_CALLBACK = new DiffUtil.ItemCallback<Receipt>()
    {
        @Override
        public boolean areItemsTheSame(@NonNull Receipt oldItem, @NonNull Receipt newItem)
        {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Receipt oldItem, @NonNull Receipt newItem)
        {
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getShortDescription().equals(newItem.getShortDescription()) &&
                    oldItem.getLargeDescription().equals(newItem.getLargeDescription()) &&
                    oldItem.getImageUri().equals(newItem.getImageUri());
        }
    };

    @NonNull
    @Override
    public ReceiptsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.receipts_list_row, null);

        return new ReceiptsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReceiptsViewHolder holder, int position)
    {
        Receipt currentReceipt = getItem(position);

        holder.titleTextView.setText(currentReceipt.getTitle());
        holder.shortDescriptionTextView.setText(currentReceipt.getShortDescription());
        final Uri uri = Uri.parse(currentReceipt.getImageUri());

        Glide.with(holder.itemView).load(uri).into(holder.receiptImageView);
//        holder.receiptImageView.setImageURI(uri);
    }

    class ReceiptsViewHolder extends MyRecyclerView.ViewHolder
    {
        ImageView receiptImageView;
        TextView titleTextView;
        TextView shortDescriptionTextView;

        ReceiptsViewHolder(@NonNull View itemView)
        {
            super(itemView);

            this.receiptImageView = itemView.findViewById(R.id.listingImageId);
            this.titleTextView = itemView.findViewById(R.id.listingTitleTextId);
            this.shortDescriptionTextView = itemView.findViewById(R.id.listingShortDescriptionTextId);

            itemView.setOnClickListener(v ->
            {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION)
                    listener.onRecyclerViewItemClick(v, getItem(position));
            });
        }
    }

    public interface RecyclerViewClickListener
    {
        void onRecyclerViewItemClick(View view, Receipt receipt);
    }

    public void setOnRecyclerViewItemClickListener(RecyclerViewClickListener listener)
    {
        this.listener = listener;
    }
}
