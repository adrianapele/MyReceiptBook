package com.example.myreceiptbook;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class ReceiptsViewHolder extends RecyclerView.ViewHolder
{
    ImageView receiptImageView;
    TextView titleTextView;
    TextView shortDescriptionTextView;

    ReceiptsViewHolder(@NonNull View itemView)
    {
        super(itemView);

        this.receiptImageView = itemView.findViewById(R.id.receiptImageId);
        this.titleTextView = itemView.findViewById(R.id.receiptTitleId);
        this.shortDescriptionTextView = itemView.findViewById(R.id.receiptShortDescriptionId);
    }
}
