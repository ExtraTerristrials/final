package com.extraterristrial.healthmanagementsystem;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.extraterristrial.healthmanagementsystem.databaseschema.DatabaseManager;

public class HomePageActivity extends AppCompatActivity {
    ListView profilelist;
    Toolbar toolbar;
    ProfileListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        profilelist=(ListView) findViewById(R.id.profile_list);

        toolbar.setTitle("Home");
        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.add_profile) {
                    Fragment createProfileFragment = new CreateProfileFragment();
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction().add(R.id.main_layout, createProfileFragment);
                    ft.commit();
                }
                return true;
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Under ConstrucTion", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        try{
            adapter=new ProfileListAdapter(this,new DatabaseManager(this,null,null,DatabaseManager.DATABASE_VERSION).getUserList());
            profilelist.setAdapter(adapter);
        }catch(NullPointerException e){
            Toast.makeText(this,"Profile List Empty",Toast.LENGTH_SHORT).show();
        }

    }
    public void additional(View view)
    {
        switch (view.getId())
        {
            case R.id.help_button:
            {
                Fragment helpPageFragment=new HelpPageFragment();
                FragmentManager fm=getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction().add(R.id.main_layout,helpPageFragment);
                ft.commit();
            }break;
            case R.id.pt_button:
            {

            }break;
            case R.id.ad_button:
            {

            }break;
        }
    }

}
