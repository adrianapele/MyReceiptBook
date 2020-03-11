package com.example.myreceiptbook.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.myreceiptbook.R;
import com.example.myreceiptbook.model.Receipt;
import com.example.myreceiptbook.model.ReceiptViewModel;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import timber.log.Timber;

public class CreateEditFragment extends Fragment
{
    public static final String CREATE_EDIT_FRAGMENT_TAG = "createEditFragment";
    public static final String REQUEST_ADD = "requestAdd";
    public static final String REQUEST_EDIT = "requestEdit";

    private static final String CHOOSE_PHOTO_FROM_GALLERY_OPTION = "Choose from Gallery";
    private static final String TAKE_PHOTO_OPTION = "Take Photo";
    private static final String CANCEL_OPTION = "Cancel";

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int TAKE_PHOTO_REQUEST = 2;

    private ImageView imageView;
    private EditText titleEditText;
    private EditText shortDescEditText;
    private EditText longDescEditText;

    private ReceiptViewModel receiptViewModel;
    private String requestType;

    private String currentPhotoPath;
    private Uri currentImageUri;

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
        final View rootView = inflater.inflate(R.layout.fragment_create_edit, container, false);
        imageView = rootView.findViewById(R.id.createEditImageViewId);
        titleEditText = rootView.findViewById(R.id.createEditTitleEditTextId);
        shortDescEditText = rootView.findViewById(R.id.createEditShortDescEditTextId);
        longDescEditText = rootView.findViewById(R.id.createEditLongDescEditTextId);

        receiptViewModel = ViewModelProviders.of(getActivity()).get(ReceiptViewModel.class);
        final Receipt currentSelectedReceipt = receiptViewModel.getCurrentSelectedReceipt().getValue();

        imageView.setOnClickListener(v -> selectImage(v.getContext()));

        if (currentSelectedReceipt != null && REQUEST_EDIT.equals(requestType))
        {
            titleEditText.setText(currentSelectedReceipt.getTitle());
            shortDescEditText.setText(currentSelectedReceipt.getShortDescription());
            longDescEditText.setText(currentSelectedReceipt.getLargeDescription());

            Picasso.with(getContext())
                    .load(Uri.parse(currentSelectedReceipt.getImageUri())/* + ".png"*/)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(imageView);
        }

        return rootView;
    }

    private void selectImage(Context context)
    {
        CharSequence[] options = {CHOOSE_PHOTO_FROM_GALLERY_OPTION,
        TAKE_PHOTO_OPTION, CANCEL_OPTION};

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Choose receipt picture");
        alertDialogBuilder.setItems(options, (dialog, which) ->
        {
            final CharSequence option = options[which];

            if (TAKE_PHOTO_OPTION.equals(option))
                takePhoto();
            else if (CHOOSE_PHOTO_FROM_GALLERY_OPTION.equals(option))
                choosePhotoFromGallery();
            else if (CANCEL_OPTION.equals(option))
                dialog.dismiss();
        });

        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void takePhoto()
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null)
        {
            File photoFile = null;
            try
            {
                photoFile = createImageFile();
            }
            catch (IOException ex)
            {
                Toast.makeText(getContext(), "Could not create image", Toast.LENGTH_SHORT).show();
            }
            // Continue only if the File was successfully created
            if (photoFile != null)
            {
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        "com.example.android.fileprovider",
                        photoFile);

                Timber.i("Current image uri: %s", photoURI);
                currentImageUri = photoURI;
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, TAKE_PHOTO_REQUEST);
            }
        }
    }

    private File createImageFile() throws IOException
    {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "jpg_" + timeStamp + "_";
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = imageFile.getAbsolutePath();

        Timber.i("Current image path: %s", currentPhotoPath);

        return imageFile;
    }

    private void choosePhotoFromGallery()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null)
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
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

        final String imagePath = currentImageUri.toString();

        if (title.trim().isEmpty() || shortDesc.trim().isEmpty()
                || longDesc.trim().isEmpty()
                || imagePath.trim().isEmpty())
        {
            Toast.makeText(getContext(), "Please fill in all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        final Receipt currentSelectedReceipt = receiptViewModel.getCurrentSelectedReceipt().getValue();

        if (REQUEST_ADD.equals(requestType) && currentSelectedReceipt == null)
        {
            Receipt newReceipt = new Receipt(title, shortDesc, longDesc, imagePath);
            receiptViewModel.insert(newReceipt);

            Toast.makeText(getContext(), "Receipt saved", Toast.LENGTH_SHORT).show();
        }
        else if (REQUEST_EDIT.equals(requestType) && currentSelectedReceipt != null)
        {
            Receipt newReceipt = new Receipt(title, shortDesc, longDesc, imagePath);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK
                && data != null && data.getData() != null)
        {
            final Uri imageUri = data.getData();
            Picasso.with(getContext())
                    .load(imageUri)
                    .into(imageView);
        }
        else if (requestCode == TAKE_PHOTO_REQUEST && resultCode == Activity.RESULT_OK)
        {
            Bitmap photo =  BitmapFactory.decodeFile(currentPhotoPath);
            imageView.setImageBitmap(photo);
        }
    }
}