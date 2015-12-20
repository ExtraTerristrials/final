package com.extraterristrial.healthmanagementsystem;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.extraterristrial.healthmanagementsystem.databaseschema.databaseobjects.DietInformation;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

/**
 * Created by Jewel on 12/18/2015.
 */
public class CreateDietFragment extends DialogFragment implements View.OnClickListener {
    View time;
    View title;
    View menu;
    View repeat;
    EditText foodName;
    TextView timeView, repeatView, menuView, titleView;
    ListView foodList;
    private FoodAdapter foodAdapter;
    CheckedTextView reminder;
    private DietInformation dietInformation;
    private int profile_id;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.create_diet_layout, null);
        dietInformation=new DietInformation();
        profile_id=getArguments().getInt("profileId");
        time = view.findViewById(R.id.timeLayout);
        title = view.findViewById(R.id.titleButtonLayout);
        menu = view.findViewById(R.id.menuButtonLayout);
        repeat = view.findViewById(R.id.repeatLayout);
        reminder = (CheckedTextView) view.findViewById(R.id.reminder);
        timeView = (TextView) view.findViewById(R.id.time);
        titleView = (TextView) view.findViewById(R.id.title);
        menuView = (TextView) view.findViewById(R.id.foodManu);
        repeatView = (TextView) view.findViewById(R.id.repeat);
        reminder = (CheckedTextView) view.findViewById(R.id.reminder);
        time.setOnClickListener(this);
        repeat.setOnClickListener(this);
        title.setOnClickListener(this);
        menu.setOnClickListener(this);
        reminder.setOnClickListener(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        if (dietInformation != null) {
                timeView.setText(dietInformation.getFoodTime());
                titleView.setText(dietInformation.getFoodTitle());
                menuView.setText(dietInformation.getFoodItem());
                repeatView.setText(dietInformation.getWeekday());
                repeat.setFocusable(true);
                repeat.setClickable(true);
               // repeat.setBackgroundResource(R.drawable.button_layout_disable_background);
                try{
                if (dietInformation.getReminder().equalsIgnoreCase("yes")) {
                    reminder.setChecked(true);
                    reminder.setCheckMarkDrawable(android.R.drawable.checkbox_on_background);
                } else {
                    reminder.setChecked(false);
                    reminder.setCheckMarkDrawable(android.R.drawable.checkbox_off_background);
                }
            }catch (NullPointerException e){
                e.printStackTrace();
            }
            builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    updateDietInformation();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        } else {
            builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    saveDietInformation();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }
        AlertDialog dialog=builder.create();
        dialog.show();
        Button positive=dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        positive.setBackgroundDrawable(getResources().getDrawable(R.drawable.edit_text_background));
        positive.setTextColor(getResources().getColor(R.color.colorRed));
        Button negative=dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        negative.setBackgroundDrawable(getResources().getDrawable(R.drawable.edit_text_background));
        negative.setTextColor(getResources().getColor(R.color.colorRed));
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.dialog_background));
        return dialog;
    }

    private void saveDietInformation() {

    }

    private void updateDietInformation() {

    }

    boolean[] checkItem = {false, false, false, false, false, false, false};

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.timeLayout:
                showTimePicker();
                break;
            case R.id.repeatLayout:
                showRepeatPicker();
                break;
            case R.id.titleButtonLayout:
                showTitlePicker();
                break;
            case R.id.menuButtonLayout:
                showMenuPicker();
                break;
              case R.id.add:
              foodAdapter.add(foodName.getText().toString());
              foodAdapter.notifyDataSetChanged();
              foodName.setText("");
              break;
            case R.id.reminder:
                if (reminder.isChecked()) {
                    reminder.setCheckMarkDrawable(android.R.drawable.checkbox_off_background);
                    reminder.setChecked(false);
                } else {
                    reminder.setCheckMarkDrawable(android.R.drawable.checkbox_on_background);
                    reminder.setChecked(true);
                }
                break;
        }
    }

    private void showMenuPicker() {
        View view = getActivity().getLayoutInflater().inflate(R.layout.food_menu_layout, null);
        view.findViewById(R.id.add).setOnClickListener(this);
        foodName = (EditText) view.findViewById(R.id.foodName);
        foodName.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        foodList = (ListView) view.findViewById(R.id.foodList);
        String foods = menuView.getText().toString();
        foodAdapter = new FoodAdapter(getActivity());
        if (foods != null && !foods.isEmpty() && !foods.equalsIgnoreCase("Not set")) {
            foodAdapter.addAll(Arrays.asList(foods.split(",")));
            foodAdapter.notifyDataSetChanged();
        }
        foodList.setAdapter(foodAdapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (foodAdapter.getCount() == 0) menuView.setText("Not set");
                else {
                    String s = "";
                    for (int i = 0; i < foodAdapter.getCount(); i++) {
                        s += foodAdapter.getItem(i) + ",";
                    }
                    menuView.setText(s.substring(0, s.length() - 1));
                }

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
        Button positive=dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        positive.setBackgroundDrawable(getResources().getDrawable(R.drawable.edit_text_background));
        positive.setTextColor(getResources().getColor(R.color.colorRed));
        Button negative=dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        negative.setBackgroundDrawable(getResources().getDrawable(R.drawable.edit_text_background));
        negative.setTextColor(getResources().getColor(R.color.colorRed));
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.dialog_background));
    }

    private void showTitlePicker() {
        final EditText titleBox = new EditText(getActivity());
        titleBox.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        titleBox.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        titleBox.setHint("Add a title");
        if (!titleView.getText().toString().equalsIgnoreCase("Not set")) {
            titleBox.setText(titleView.getText());
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(titleBox);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String text = titleBox.getText().toString();
                if (text == null || text.isEmpty()) titleView.setText("Not set");
                else titleView.setText(text);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
        Button positive=dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        positive.setBackgroundDrawable(getResources().getDrawable(R.drawable.edit_text_background));
        positive.setTextColor(getResources().getColor(R.color.colorRed));
        Button negative=dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        negative.setBackgroundDrawable(getResources().getDrawable(R.drawable.edit_text_background));
        negative.setTextColor(getResources().getColor(R.color.colorRed));
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.dialog_background));
    }

    private void showRepeatPicker() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMultiChoiceItems(R.array.weekDay, checkItem, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    checkItem[which] = true;
                }
            }
        });
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String repeatDay = "";
                String[] days = getResources().getStringArray(R.array.weekDay);
                for (int i = 0; i < 7; i++) {
                    if (checkItem[i]) repeatDay += days[i] + ",";
                }
                if (repeatDay.length() == 0) repeatView.setText("Not set");
                else repeatView.setText(repeatDay.substring(0, repeatDay.length() - 1));
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
        Button positive=dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        positive.setBackgroundDrawable(getResources().getDrawable(R.drawable.edit_text_background));
        positive.setTextColor(getResources().getColor(R.color.colorBlack));
        Button negative=dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        negative.setBackgroundDrawable(getResources().getDrawable(R.drawable.edit_text_background));
        negative.setTextColor(getResources().getColor(R.color.colorBlack));
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.dialog_background_2));
    }

    private void showTimePicker() {
        final TimePicker timePicker = new TimePicker(getActivity());
        timePicker.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(timePicker);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());
                SimpleDateFormat format = new SimpleDateFormat("hh:mm a");
                String time = format.format(calendar.getTime());
                timeView.setText(time);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
        Button positive=dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        positive.setBackgroundDrawable(getResources().getDrawable(R.drawable.edit_text_background));
        positive.setTextColor(getResources().getColor(R.color.colorRed));
        Button negative=dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        negative.setBackgroundDrawable(getResources().getDrawable(R.drawable.edit_text_background));
        negative.setTextColor(getResources().getColor(R.color.colorRed));
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.dialog_background));

    }
}
