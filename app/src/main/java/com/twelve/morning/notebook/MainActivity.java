package com.twelve.morning.notebook;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


public class MainActivity extends AppCompatActivity {

    private ListView list_view;
    public NotesListAdapter adapter = null;
    private Sorting sorting = Sorting.CREATION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupButtons();
        reloadNotes(sorting);
      
        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, EditNoteActivity.class);
                Note note = (Note)adapterView.getItemAtPosition(i);
                intent.putExtra("title", note.getTitle());
                startActivity(intent);
            }
        });
    }
  
    protected void onResume() {
        super.onResume();
        reloadNotes(sorting);
    }

    private void reloadNotes(Sorting sorting) {
        this.adapter = new NotesListAdapter(DatabaseWrapper.getInstance().getNotes(sorting), this);
        list_view = findViewById(R.id.list_notes);
        list_view.setAdapter(this.adapter);
    }

    private void setupButtons(){
        FloatingActionButton create_note_btn = findViewById(R.id.bt_create);
        create_note_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switch_to_create_note = new Intent(MainActivity.this,
                        CreateNoteActivity.class);
                startActivity(switch_to_create_note);
            }
        });

        Button sort_date_button = findViewById(R.id.bt_sort_by_creation);

        sort_date_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sorting = Sorting.CREATION;
                reloadNotes(sorting);
            }
        });

        Button sort_title_button = findViewById(R.id.bt_sort_by_title);

        sort_title_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sorting = Sorting.TITLE;
                reloadNotes(sorting);
            }
        });
    }
}

