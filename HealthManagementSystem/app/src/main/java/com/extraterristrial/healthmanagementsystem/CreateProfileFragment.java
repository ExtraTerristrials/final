package com.extraterristrial.healthmanagementsystem;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import com.extraterristrial.healthmanagementsystem.databaseschema.DatabaseManager;
import com.extraterristrial.healthmanagementsystem.databaseschema.databaseobjects.UserInformation;

public class CreateProfileFragment extends Fragment {
    private Spinner gender;
    private Spinner status;
    private EditText name;
    private EditText age;
    private EditText number;
    private EditText email;
    private ImageView picture;
    private ImageButton camera;
    private ImageButton gallary;
    private Toolbar toolbar;
    private Bitmap bitmap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_profile_layout, container, false);
        gender = (Spinner) view.findViewById(R.id.spinner);
        status=(Spinner)view.findViewById(R.id.status_spinner);
        ArrayAdapter<CharSequence> genderadapter=ArrayAdapter.createFromResource(getContext(), R.array.gender, R.layout.spinner_item_layout);
        genderadapter.setDropDownViewResource(R.layout.spinner_item_layout);
        gender.setAdapter(genderadapter);
        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                gender.setSelection(0);
            }
        });
        ArrayAdapter<CharSequence> statusadapter=ArrayAdapter.createFromResource(getContext(),R.array.relation,R.layout.spinner_item_layout);
        statusadapter.setDropDownViewResource(R.layout.spinner_item_layout);
        status.setAdapter(statusadapter);
        status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                status.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                status.setSelection(0);
            }
        });
        name = (EditText) view.findViewById(R.id.edit_profilename);
        age = (EditText) view.findViewById(R.id.edit_profileage);
        number = (EditText) view.findViewById(R.id.edit_profilenumber);
        email = (EditText) view.findViewById(R.id.edit_email);
        picture = (ImageView) view.findViewById(R.id.profileimage);
        picture.setImageResource(R.mipmap.noimage);
        camera = (ImageButton) view.findViewById(R.id.camera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (cameraIntent.resolveActivity(getContext().getPackageManager()) != null) {
                    startActivityForResult(cameraIntent, 1);
                }
            }
        });
        gallary = (ImageButton) view.findViewById(R.id.gallary);
        gallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fileIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                fileIntent.setType("image/*");
                startActivityForResult(Intent.createChooser(fileIntent, "select file"), 2);
            }
        });
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle("Create Profile");
        toolbar.inflateMenu(R.menu.menu_create_profile);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.save_profile) {
                    saveValues();
                    Intent intent = new Intent(getActivity(), HomePageActivity.class);
                    startActivity(intent);
                }
                return true;
            }
        });
        return view;
    }

    private void saveValues() {

        UserInformation userInformation = new UserInformation();
        userInformation.setUserName(name.getText().toString());
        userInformation.setUserAge(age.getText().toString());
        userInformation.setUserGender(gender.getSelectedItem().toString());
        userInformation.setUserRelationshipStatus(status.getSelectedItem().toString());
        userInformation.setUserEmail(email.getText().toString());
        userInformation.setUserPhoneNo(number.getText().toString());
        userInformation.setUserPic(bitmap);
        DatabaseManager db = new DatabaseManager(getContext(), null, null, 1);
        db.Insert(userInformation);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode == 1) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                picture.setImageBitmap(imageBitmap);
                bitmap = imageBitmap;
            } else if (requestCode == 2) {
                Uri selectedImageUri = data.getData();
                String[] projection = {MediaStore.MediaColumns.DATA};
                Cursor cursor = getActivity().managedQuery(selectedImageUri, projection, null, null,
                        null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                cursor.moveToFirst();
                String selectedImagePath = cursor.getString(column_index);
                Bitmap bm;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(selectedImagePath, options);
                final int REQUIRED_SIZE = 200;
                int scale = 1;
                while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                        && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                    scale *= 2;
                options.inSampleSize = scale;
                options.inJustDecodeBounds = false;

                bm = BitmapFactory.decodeFile(selectedImagePath, options);
                picture.setImageBitmap(bm);
                bitmap = bm;

            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
