package com.example.myreceiptbook;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

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
    }
}
