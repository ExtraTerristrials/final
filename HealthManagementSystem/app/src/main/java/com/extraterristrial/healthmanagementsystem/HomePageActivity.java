package com.extraterristrial.healthmanagementsystem;

import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.ListView;

import com.extraterristrial.healthmanagementsystem.databaseschema.DatabaseManager;

public class HomePageActivity extends AppCompatActivity {
    ListView profilelist;
    Toolbar toolbar;
    ProfileListAdapter adapter;
    Bundle bundle=new Bundle();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        profilelist=(ListView) findViewById(R.id.profile_list);
        toolbar.setTitle("Home");
        toolbar.inflateMenu(R.menu.add_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.add_profile) {
                    Fragment createProfileFragment = new CreateProfileFragment();
                    FragmentManager fm = getSupportFragmentManager();
                    Bundle bundle=new Bundle();
                    bundle.putString("origin","add");
                    createProfileFragment.setArguments(bundle);
                    FragmentTransaction ft = fm.beginTransaction().add(R.id.home_layout, createProfileFragment);
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
            new DatabaseManager(this,null,null,DatabaseManager.DATABASE_VERSION).DeleteAll();
            Fragment helpPageFragment=new HelpPageFragment();
            bundle.putString("type","automatic");
            helpPageFragment.setArguments(bundle);
            FragmentManager fm=getSupportFragmentManager();
            FragmentTransaction ft=fm.beginTransaction().add(R.id.home_layout,helpPageFragment);
            ft.commit();
        }
        profilelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getBaseContext(),DashBoardActivity.class);
                intent.putExtra("profile_id",position);
                startActivity(intent);

            }
        });

    }
    public void additional(View view)
    {
        switch (view.getId())
        {
            case R.id.help_button:
            {
                Fragment helpPageFragment=new HelpPageFragment();
                bundle.putString("type","manual");
                helpPageFragment.setArguments(bundle);
                FragmentManager fm=getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction().add(R.id.home_layout,helpPageFragment);
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
