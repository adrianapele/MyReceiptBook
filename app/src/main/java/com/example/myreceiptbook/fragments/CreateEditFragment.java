package com.example.myreceiptbook.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
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

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import timber.log.Timber;

public class CreateEditFragment extends Fragment
{
    static final String CREATE_EDIT_FRAGMENT_TAG = "createEditFragment";

    static final String REQUEST_ADD = "requestAdd";
    static final String REQUEST_EDIT = "requestEdit";

    private static final String CHOOSE_PHOTO_FROM_GALLERY_OPTION = "Choose from Gallery";
    private static final String TAKE_PHOTO_OPTION = "Take Photo";
    private static final String CANCEL_OPTION = "Cancel";

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int TAKE_PHOTO_REQUEST = 2;

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    private static final int READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 110;

    private ImageView imageView;
    private EditText titleEditText;
    private EditText shortDescEditText;
    private EditText longDescEditText;

    private ReceiptViewModel receiptViewModel;
    private String requestType;

    private String imagePath;

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

        titleEditText = rootView.findViewById(R.id.createEditTitleEditTextId);
        shortDescEditText = rootView.findViewById(R.id.createEditShortDescEditTextId);
        longDescEditText = rootView.findViewById(R.id.createEditLongDescEditTextId);
        imageView = rootView.findViewById(R.id.createEditImageViewId);
        imageView.setOnClickListener(v -> selectImage(v.getContext()));

        receiptViewModel = ViewModelProviders.of(getActivity()).get(ReceiptViewModel.class);
        final Receipt currentSelectedReceipt = receiptViewModel.getCurrentSelectedReceipt().getValue();

        if (currentSelectedReceipt != null && REQUEST_EDIT.equals(requestType))
        {
            titleEditText.setText(currentSelectedReceipt.getTitle());
            shortDescEditText.setText(currentSelectedReceipt.getShortDescription());
            longDescEditText.setText(currentSelectedReceipt.getLargeDescription());
            imageView.setImageURI(Uri.parse(currentSelectedReceipt.getImageUri()));
        }

        if (REQUEST_EDIT.equals(requestType))
            getActivity().setTitle("Edit Receipt");
        else
            getActivity().setTitle("Add Receipt");

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

            if (TAKE_PHOTO_OPTION.contentEquals(option))
                takePhotoWithPermissions(context);
            else if (CHOOSE_PHOTO_FROM_GALLERY_OPTION.contentEquals(option))
                choosePhotoFromGalleryWithPermissions(context);
            else if (CANCEL_OPTION.contentEquals(option))
                dialog.dismiss();
        });

        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void takePhotoWithPermissions(Context context)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            final int cameraPermission = context.checkSelfPermission(Manifest.permission.CAMERA);
            if (cameraPermission != PackageManager.PERMISSION_GRANTED)
            {
                if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA))
                {
                    new AlertDialog.Builder(context)
                            .setMessage("You need to allow access to Camera in order to be able to take photos")
                            .setPositiveButton("OKAY", (dialog, which) -> requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE))
                            .setNegativeButton("Cancel", null)
                            .create()
                            .show();
                    return;
                }
                else
                {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
                    return;
                }
            }
        }

        takePhoto(context);
    }

    private void takePhoto(Context context)
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null)
        {
            File photoFile = null;
            try
            {
                photoFile = createImageFile(context);
            }
            catch (IOException ex)
            {
                Toast.makeText(getContext(), "Could not create image", Toast.LENGTH_SHORT).show();
            }

            if (photoFile != null)
            {
                Uri photoURI = null;
                try
                {
                    photoURI = FileProvider.getUriForFile(context,
                            "com.example.android.fileprovider",
                            photoFile);
                }
                catch (IllegalArgumentException ex)
                {
                    Toast.makeText(getContext(), "Could not retrieve image from internal location", Toast.LENGTH_SHORT).show();
                }

                if (photoURI != null)
                {
                    imagePath = photoURI.toString();
                    Timber.i("Current imagePath: %s", imagePath);

                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, TAKE_PHOTO_REQUEST);
                }
            }
        }
    }

    private File createImageFile(Context context) throws IOException
    {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "jpg_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }

    private void choosePhotoFromGalleryWithPermissions(Context context)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            final int readExternalStoragePermission = context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            if (readExternalStoragePermission != PackageManager.PERMISSION_GRANTED)
            {
                if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE))
                {
                    new AlertDialog.Builder(context)
                            .setMessage("You need to allow access to your storage in order to be able to take and choose photos")
                            .setPositiveButton("OKAY", ((dialog, which) -> requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE)))
                            .setNegativeButton("Cancel", null)
                            .create()
                            .show();

                    return;
                }
                else
                {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE);
                    return;
                }
            }
        }

        choosePhotoFromGallery(context);
    }

    private void choosePhotoFromGallery(Context context)
    {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        if (intent.resolveActivity(context.getPackageManager()) != null)
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK
                && data != null && data.getData() != null)
        {
            Uri selectedImage = data.getData();

            if (selectedImage != null)
            {
                String path = getPathFromURI(selectedImage);
                if (path != null)
                {
                    File file = new File(path);
                    imagePath = Uri.fromFile(file).toString();
                    imageView.setImageURI(Uri.parse(imagePath));
                }
            }
            Toast.makeText(getContext(), "Could not load image", Toast.LENGTH_SHORT).show();
        }
        else if (requestCode == TAKE_PHOTO_REQUEST && resultCode == Activity.RESULT_OK)
        {
            imageView.setImageURI(Uri.parse(imagePath));
        }
    }

    public String getPathFromURI(Uri contentUri)
    {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(contentUri, proj, null, null, null);

        if (cursor.moveToFirst())
        {
            int column_index = cursor.getColumnIndex(proj[0]);
            return cursor.getString(column_index);
        }
        cursor.close();
        return null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE)
        {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                takePhoto(getContext());
            else
                Toast.makeText(getContext(), "Camera Permission Not Granted", Toast.LENGTH_SHORT).show();
        }
        else if (requestCode == READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE)
        {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                choosePhotoFromGallery(getContext());
            else
                Toast.makeText(getContext(), "Read External Storage Permission Not Granted", Toast.LENGTH_SHORT).show();
        }
        else
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater)
    {
        inflater.inflate(R.menu.create_edit_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if (item.getItemId() == R.id.save_receipt)
        {
            saveReceipt();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveReceipt()
    {
        final String title = titleEditText.getText().toString();
        final String shortDesc = shortDescEditText.getText().toString();
        final String longDesc = longDescEditText.getText().toString();

        if (title.trim().isEmpty() || shortDesc.trim().isEmpty()
                || longDesc.trim().isEmpty()
                || imagePath.isEmpty())
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

    void setRequestType(String requestType)
    {
        this.requestType = requestType;
    }
}