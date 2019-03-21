package com.twelve.morning.notebook;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handleNoteCreation();

    }


    private void handleNoteCreation(){
        FloatingActionButton create_note_btn = findViewById(R.id.bt_create);
        create_note_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout view_group = MainActivity.this.findViewById(R.id.pw_create_note);
                LayoutInflater layoutInflater = (LayoutInflater)
                        MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.popup_window, view_group);
                System.out.println(customView);
                PopupWindow popup_window = new PopupWindow(customView,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                popup_window.showAtLocation(view, Gravity.CENTER, 0, 0);
            }
        });
    }
}

