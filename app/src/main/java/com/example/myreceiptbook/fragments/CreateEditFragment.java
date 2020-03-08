package com.example.myreceiptbook.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.myreceiptbook.R;
import com.example.myreceiptbook.model.Receipt;
import com.example.myreceiptbook.model.ReceiptViewModel;
import com.squareup.picasso.Picasso;

public class CreateEditFragment extends Fragment
{
    public static final String CREATE_EDIT_FRAGMENT_TAG = "createEditFragment";
    public static final String REQUEST_ADD = "requestAdd";
    public static final String REQUEST_EDIT = "requestEdit";

    private ImageView imageView;
    private EditText titleEditText;
    private EditText shortDescEditText;
    private EditText longDescEditText;

    private ReceiptViewModel receiptViewModel;
    private String requestType;

    public CreateEditFragment()
    {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
//        getActivity().setTitle("New Receipt");

        final View rootView = inflater.inflate(R.layout.fragment_create_edit, container, false);
        imageView = rootView.findViewById(R.id.createEditImageViewId);
        titleEditText = rootView.findViewById(R.id.createEditTitleEditTextId);
        shortDescEditText = rootView.findViewById(R.id.createEditShortDescEditTextId);
        longDescEditText = rootView.findViewById(R.id.createEditLongDescEditTextId);

        receiptViewModel = ViewModelProviders.of(getActivity()).get(ReceiptViewModel.class);
        final Receipt currentSelectedReceipt = receiptViewModel.getCurrentSelectedReceipt().getValue();

        if (currentSelectedReceipt != null && REQUEST_EDIT.equals(requestType))
        {
            titleEditText.setText(currentSelectedReceipt.getTitle());
            shortDescEditText.setText(currentSelectedReceipt.getShortDescription());
            longDescEditText.setText(currentSelectedReceipt.getLargeDescription());

            Picasso.with(getContext())
                    .load(Uri.parse(currentSelectedReceipt.getImageUri()) + ".png")
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(imageView);
        }

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater)
    {
        inflater.inflate(R.menu.create_edit_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.save_receipt:
                saveReceipt();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveReceipt()
    {
        final String title = titleEditText.getText().toString();
        final String shortDesc = shortDescEditText.getText().toString();
        final String longDesc = longDescEditText.getText().toString();

        if (title.trim().isEmpty() || shortDesc.trim().isEmpty() || longDesc.trim().toString().isEmpty())
        {
            Toast.makeText(getContext(), "Please fill in all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        final Receipt currentSelectedReceipt = receiptViewModel.getCurrentSelectedReceipt().getValue();
        Receipt newReceipt = new Receipt(title, shortDesc, longDesc, "");

        if (REQUEST_ADD.equals(requestType) && currentSelectedReceipt == null)
        {
            receiptViewModel.insert(newReceipt);
            Toast.makeText(getContext(), "Receipt saved", Toast.LENGTH_SHORT).show();
        }
        else if (REQUEST_EDIT.equals(requestType) && currentSelectedReceipt != null)
        {
            newReceipt.setId(currentSelectedReceipt.getId());
            receiptViewModel.update(newReceipt);
            receiptViewModel.setCurrentSelectedReceipt(newReceipt);
            Toast.makeText(getContext(), "Receipt updated", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getContext(), "Receipt could not be created", Toast.LENGTH_SHORT).show();
        }

        getActivity().onBackPressed();
    }

    public void setRequestType(String requestType)
    {
        this.requestType = requestType;
    }
}
