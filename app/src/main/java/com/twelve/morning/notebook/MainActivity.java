package com.twelve.morning.notebook;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainActivity extends AppCompatActivity {

    private ListView list_view;
    private NotesListAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        switchToCreateNoteActivity();
        reloadNotes();
      
//        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(MainActivity.this, EditNoteActivity.class);
//                Note note = (Note)adapterView.getItemAtPosition(i);
//                intent.putExtra("note", note);
//                startActivity(intent);
//            }
//        });
    }
  
    protected void onResume() {
        super.onResume();
        reloadNotes();

    }

    private void reloadNotes() {
        System.out.println(DatabaseWrapper.getInstance());
        this.adapter = new NotesListAdapter(DatabaseWrapper.getInstance().getNotes(), this);
        list_view = (ListView) findViewById(R.id.list_notes);
        list_view.setAdapter(this.adapter);
    }

    private void switchToCreateNoteActivity(){
        FloatingActionButton create_note_btn = findViewById(R.id.bt_create);
        create_note_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switch_to_create_note = new Intent(MainActivity.this,
                        CreateNoteActivity.class);
                startActivity(switch_to_create_note);
            }
        });
    }
}

