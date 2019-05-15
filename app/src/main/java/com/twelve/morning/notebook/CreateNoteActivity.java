package com.twelve.morning.notebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
        last_saved_note.setTags(Tags);
        DatabaseWrapper.getInstance().addNote(new Note(title_text, body_text, Tags));
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
