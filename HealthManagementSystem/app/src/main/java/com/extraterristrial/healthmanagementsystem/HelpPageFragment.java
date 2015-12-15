package com.extraterristrial.healthmanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.support.v7.widget.Toolbar;
import android.widget.ViewFlipper;

/**
 * Created by Jewel on 12/9/2015.
 */
public class HelpPageFragment extends Fragment {
    Toolbar toolbar;
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private ViewFlipper viewFlipper;
    private final GestureDetector detector=new GestureDetector(new SwipeGestureDetector());
    int image[]={R.mipmap.ptbutton,R.mipmap.camera,R.mipmap.file,R.mipmap.save,R.id.pt_button,R.id.help_button};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.help_page_layout,container,false);
        toolbar=(Toolbar)view.findViewById(R.id.toolbar);
        toolbar.setTitle("Help");
        toolbar.inflateMenu(R.menu.menu_main);
        viewFlipper=(ViewFlipper)view.findViewById(R.id.viewflipper);
        for (int anImage : image) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setImageResource(anImage);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        if (getArguments().getString("type").equals("automatic"))
        {
            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if (item.getItemId()==R.id.add_profile)
                    {
                        Fragment createProfileFragment=new CreateProfileFragment();
                        FragmentManager fm=getActivity().getSupportFragmentManager();
                        FragmentTransaction ft=fm.beginTransaction().replace(R.id.main_layout,createProfileFragment);
                        ft.commit();
                    }
                    return true;
                }
            });
            viewFlipper.setAutoStart(true);
            viewFlipper.setFlipInterval(3000);
            viewFlipper.startFlipping();
        } else if (getArguments().getString("type").equals("manual")) {
            toolbar.inflateMenu(R.menu.menu_help);
            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if (item.getItemId() == R.id.cross) {
                        Intent intent = new Intent(getActivity(), HomePageActivity.class);
                        startActivity(intent);
                    }
                    return true;
                }
            });
            viewFlipper.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    detector.onTouchEvent(event);
                    return true;
                }
            });
        }
        return view;
    }
    class SwipeGestureDetector extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                  //  viewFlipper.setInAnimation(AnimationUtils.loadAnimation(R.anim.left_in));
                  //  viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(R.anim.left_out));
                    viewFlipper.showNext();
                    return true;
                } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                   // viewFlipper.setInAnimation(AnimationUtils.loadAnimation(R.anim.right_in));
                   // viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(R.anim.right_out));
                    viewFlipper.showPrevious();
                    return true;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
    }
}


