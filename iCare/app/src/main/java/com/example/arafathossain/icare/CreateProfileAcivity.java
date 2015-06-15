package com.example.arafathossain.icare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CreateProfileAcivity extends AppCompatActivity {
    private Spinner bloodGroup;
    private EditText profileName;
    private EditText userName;
    private EditText email;
    private EditText contactNo;
    private EditText height;
    private EditText weight;
    private EditText dateOfBirth;
    private RadioGroup genderGroup;
    private DatabaseHelper databaseHelper;
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.actionBar);
        toolbar.setTitle(getTitle());
        toolbar.setLogo(R.mipmap.icare_icon);
        setSupportActionBar(toolbar);
        bloodGroup = (Spinner) findViewById(R.id.bloodGroup);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,R.array.bloodGroupList,R.layout.spinner_item);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_item);
        bloodGroup.setAdapter(spinnerAdapter);
        profileName = (EditText) findViewById(R.id.profileName);
        userName = (EditText) findViewById(R.id.userName);
        email = (EditText) findViewById(R.id.email);
        contactNo = (EditText) findViewById(R.id.contactNo);
        weight = (EditText) findViewById(R.id.weight);
        height = (EditText) findViewById(R.id.height);
        dateOfBirth = (EditText) findViewById(R.id.dateOfBirth);
        genderGroup = (RadioGroup) findViewById(R.id.genderGroup);
        databaseHelper = new DatabaseHelper(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create_profile,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.save){
            createProfile();
        }
        return super.onOptionsItemSelected(item);
    }
    private void createProfile(){
        if (!validateEmail()) {
            Toast.makeText(this,"Invalid email",Toast.LENGTH_LONG).show();
            return;
        }
        if (genderGroup.getCheckedRadioButtonId()==-1){
            Toast.makeText(this,"Select your gender",Toast.LENGTH_LONG).show();
            return;
        }
        Profile profile = new Profile();
        profile.setBloodGroup(bloodGroup.getSelectedItem().toString());
        profile.setProfileName(profileName.getText().toString());
        profile.setUserName(userName.getText().toString());
        profile.setEmail(email.getText().toString());
        profile.setContactNo(contactNo.getText().toString());
        profile.setHeight(height.getText().toString());
        profile.setWeight(weight.getText().toString());
        profile.setDateOfBirth(dateOfBirth.getText().toString());
        profile.setGender((genderGroup.getCheckedRadioButtonId()==R.id.male?"Male":"Female"));
        int status = databaseHelper.addProfile(profile);
        if (status==0) Toast.makeText(CreateProfileAcivity.this, "Unable to create profile", Toast.LENGTH_SHORT).show();
        else {
            Toast.makeText(CreateProfileAcivity.this, "Profile created successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.putExtra("profileName",profileName.getText());
            setResult(RESULT_OK,intent);
            finish();
        }
    }
    private boolean validateEmail(){
        Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = emailPattern.matcher(this.email.getText());
        return matcher.matches();
    }
}
