package com.twelve.morning.notebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CreateNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        finishCreateNoteActivity((Button)findViewById(R.id.bt_note_create_cancel));
        finishCreateNoteActivity((Button)findViewById(R.id.bt_note_create_save));
    }

    private void storeNote(){

    }


    private void finishCreateNoteActivity(final Button button){
        if(button.getId() == R.id.bt_note_create_cancel ||
                button.getId() == R.id.bt_note_create_save){
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(view.getId() == R.id.bt_note_create_save) storeNote();
                    Intent switch_back_to_main = new Intent(CreateNoteActivity.this,
                            MainActivity.class);
                    startActivity(switch_back_to_main);
                }
            });
        }
    }
}
