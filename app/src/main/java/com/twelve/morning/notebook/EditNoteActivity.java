package com.twelve.morning.notebook;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class EditNoteActivity extends AppCompatActivity {

    private Note last_edited_note = null;
    public Note getLastEditedNote(){
        return this.last_edited_note;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        fillTitleBody();
        finishEditNoteActivity((Button)findViewById(R.id.bt_edit_note_create_cancel));
        finishEditNoteActivity((Button)findViewById(R.id.bt_edit_note_create_save));
        finishEditNoteActivity((SearchView)findViewById(R.id.search_view_find_text));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {


        Intent intent = getIntent();
        Note note = (Note)intent.getSerializableExtra("note");

        switch (item.getItemId()){
            case R.id.option_delete:
                note.delete();
                Intent switch_back_to_main = new Intent(EditNoteActivity.this,
                        MainActivity.class);
                startActivity(switch_back_to_main);
                return true;
            case R.id.option_share:
                ShareManager.getStoragePermission(this);
                Note[] noteToEport = new Note[1];
                noteToEport[0] = note;
                String zipFileName = note.getTitle()+".zip";
                ShareManager.zip(noteToEport, zipFileName);
                startActivity(ShareManager.shareZipFile(zipFileName));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void fillTitleBody()
    {
        Intent intent = getIntent();
        Note note = (Note)intent.getSerializableExtra("note");
        EditText edit_text_title = this.findViewById(R.id.et_edit_note_title);
        EditText edit_text_body = this.findViewById(R.id.et_edit_note_body);
        edit_text_title.setText(note.getTitle());
        edit_text_body.setText(note.getBody());
    }


    private void finishEditNoteActivity(final Button button){
        if(button.getId() == R.id.bt_edit_note_create_cancel){
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent switch_back_to_main = new Intent(EditNoteActivity.this,
                            MainActivity.class);
                    startActivity(switch_back_to_main);
                }
            });
        }
        else if(button.getId() == R.id.bt_edit_note_create_save){
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = getIntent();
                    Note note = (Note)intent.getSerializableExtra("note");
                    EditText edit_text_title = findViewById(R.id.et_edit_note_title);
                    EditText edit_text_body = findViewById(R.id.et_edit_note_body);
                    note.setTitle(edit_text_title.getText().toString());
                    note.setBody(edit_text_body.getText().toString());

                    String[] tags = TagManager.parse(edit_text_body.getText().toString());
                    List<Tag> Tags = new ArrayList<>();
                    for (String tag1 : tags) {
                        Tag tag = new Tag(tag1);
                        Tags.add(tag);
                        //note.setTags(Tags);
                    }

                    note.save();

                    Intent switch_back_to_main = new Intent(EditNoteActivity.this,
                            MainActivity.class);
                    startActivity(switch_back_to_main);
                }
            });
        }
    }

    private void finishEditNoteActivity(final SearchView searchView){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                int position = TextSearcher.GetInstance().SearchNextInstance(last_edited_note, query);
                if(position == -1)
                    return false;
                else{
                    Activity activity = null; //Needs to be proper activity
                    TextSearcher.GetInstance().highlightText(activity, last_edited_note, position, query.length());
                }

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
    }
}
