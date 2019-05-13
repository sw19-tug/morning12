package com.twelve.morning.notebook;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.net.Uri;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class MainActivity extends AppCompatActivity {

    private ListView list_view;
    public NotesListAdapter adapter = null;
    private Sorting sorting = Sorting.CREATION;

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            menu.findItem(R.id.bt_darkmode).setTitle(R.string.day_mode);
        } else {
            menu.findItem(R.id.bt_darkmode).setTitle(R.string.night_mode);
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.bt_darkmode:
                if (AppCompatDelegate.getDefaultNightMode() != AppCompatDelegate.MODE_NIGHT_YES) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    recreate();
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    recreate();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseWrapper.getInstance().createDatabase(getApplicationContext());
        setContentView(R.layout.activity_main);
        setupButtons();
        reloadNotes(sorting);
        exportNotes();
    }
  
    protected void onResume() {
        super.onResume();
        reloadNotes(sorting);
    }

    public void reloadNotes(Sorting sorting) {
        if (sorting != null){
            this.sorting = sorting;
        }
        this.adapter = new NotesListAdapter(DatabaseWrapper.getInstance().getNotes(this.sorting), this);
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

    private void exportNotes(){
        FloatingActionButton export_note_btn = findViewById(R.id.bt_export);
        final Activity self = this;
        export_note_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareManager.getStoragePermission(self);
                String zipFileName = "exported_notes.zip";
                Note[] notesToEport = DatabaseWrapper.getInstance().getNotes(Sorting.TITLE);
                ShareManager.zip(notesToEport, zipFileName);
                startActivity(ShareManager.shareZipFile(zipFileName));
            }
        });
    }

}

