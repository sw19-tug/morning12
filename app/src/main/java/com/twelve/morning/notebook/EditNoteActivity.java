package com.twelve.morning.notebook;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;


import com.google.android.gms.maps.model.LatLng;

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

        fillLocation();
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

    private void fillLocation(){
        Intent intent = getIntent();
        Note note = (Note)intent.getSerializableExtra("note");
        TextView location_text_view = this.findViewById(R.id.tv_note_location);
        String address = note.getAddress();
        location_text_view.setText(getString(R.string.created_at_location, address));
    }

    private void fillTitleBody()
    {
        Intent intent = getIntent();
        Note note = (Note)intent.getSerializableExtra("note");
        EditText edit_text_title = this.findViewById(R.id.et_edit_note_title);
        TextView textView = (TextView) findViewById(R.id.et_edit_note_body);
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
        Intent intent = getIntent();
        final Note note = (Note)intent.getSerializableExtra("note");
        final Activity self = this;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                System.out.println("onQueryTextSubmit "+query);
                int position = TextSearcher.GetInstance().SearchNextInstance(note, query);
                if(position == -1){
                    showAlert("Warning", "'"+query+"' not found", "Ok");
                    return false;
                }
                else{
                    TextSearcher.GetInstance().highlightText(self, note, position, query.length());
                }

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
    }

    private void showAlert(String title, String message, String positive_message) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setPositiveButton(positive_message, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alert.setTitle(title);
        alert.setMessage(message);

        alert.create().show();
    }
}
