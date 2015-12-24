package com.extraterristrial.healthmanagementsystem.medicine;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.extraterristrial.healthmanagementsystem.R;

public class MedicineActivity extends AppCompatActivity {

    FragmentManager fManager=getSupportFragmentManager();
    FragmentTransaction fTransaction=fManager.beginTransaction();
    MedicineProfileFragment medicineProfileFragment=new MedicineProfileFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);

        Toast.makeText(this,"User No. "+getIntent().getExtras().getInt("userId"),Toast.LENGTH_SHORT).show();

        fTransaction.add(R.id.fragment_container, medicineProfileFragment);
        fTransaction.commit();
    }
}
