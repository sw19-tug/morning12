package com.twelve.morning.notebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditNoteActivity extends AppCompatActivity {

    private Note last_edited_note = null;
    public Note getLastEditedNote(){
        return this.last_edited_note;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        finishEditNoteActivity((Button)findViewById(R.id.bt_edit_note_create_cancel));
        finishEditNoteActivity((Button)findViewById(R.id.bt_edit_note_create_save));
    }


    private void finishEditNoteActivity(final Button button){
        if(button.getId() == R.id.bt_edit_note_create_cancel ||
                button.getId() == R.id.bt_edit_note_create_save){
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent switch_back_to_main = new Intent(EditNoteActivity.this,
                            MainActivity.class);
                    startActivity(switch_back_to_main);
                }
            });
        }
    }
}
