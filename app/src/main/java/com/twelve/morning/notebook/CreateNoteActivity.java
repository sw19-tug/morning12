package com.twelve.morning.notebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.TextWatcher;
import android.text.util.Linkify;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CreateNoteActivity extends AppCompatActivity {

    private Note last_saved_note = null;
    public Note getLastSavedNote(){
        return this.last_saved_note;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        TextView textView = (TextView) findViewById(R.id.et_note_body);
        textView.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Linkify.addLinks((Spannable) s, Linkify.WEB_URLS);
            }

            @Override
            public void afterTextChanged(Editable s) {
                Linkify.addLinks(s, Linkify.WEB_URLS);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Nothing to do
            }

        });

        finishCreateNoteActivity((Button)findViewById(R.id.bt_note_create_cancel));
        finishCreateNoteActivity((Button)findViewById(R.id.bt_note_create_save));
    }

    private void storeNote() {
        EditText title = CreateNoteActivity.this.findViewById(R.id.et_note_title);
        EditText body = CreateNoteActivity.this.findViewById(R.id.et_note_body);
        String title_text = title.getText().toString();
        String body_text = body.getText().toString();
        if (!(title_text.isEmpty() && body_text.isEmpty())) {
            last_saved_note = new Note(title_text, body_text);
        }

        String[] tags = TagManager.parse(body_text);
        List<Tag> Tags = new ArrayList<>();
        for (String tag1 : tags) {
            Tag tag = new Tag(tag1);
            Tags.add(tag);
        }

        DatabaseWrapper.getInstance().addNote(new Note(title_text, body_text));
    }


    private void finishCreateNoteActivity(final Button button){
        if(button.getId() == R.id.bt_note_create_cancel ||
                button.getId() == R.id.bt_note_create_save){
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(view.getId() == R.id.bt_note_create_save)
                    {
                        storeNote();
                    }
                    Intent switch_back_to_main = new Intent(CreateNoteActivity.this,
                            MainActivity.class);
                    startActivity(switch_back_to_main);
                }
            });
        }
    }
}
