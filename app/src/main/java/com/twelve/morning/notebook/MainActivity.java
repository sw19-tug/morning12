package com.twelve.morning.notebook;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
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
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    public NotesListAdapter adapter = null;
    private Sorting sorting = Sorting.CREATION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupButtons();
        reloadNotes(sorting);
        exportNotes();      

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
        reloadNotes(sorting);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.import_menu, menu);
        return true;
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
        export_note_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String zipFileName = "exported_notes.zip";
                //zip(notes, zipFileName);
                shareZipFile(zipFileName);
            }
        });
    }

    private void zip(String[] _notes, String zipFileName) {
        int BUFFER = 1000;
        try {
            getStoragePermission();
            BufferedInputStream noteBuffered = null;
            FileOutputStream dest = new FileOutputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + zipFileName);
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
            byte data[] = new byte[BUFFER];

            for (int i = 0; i < _notes.length; i++) {
                //FileInputStream fi = new FileInputStream(_files[i]);
                InputStream inputStream = new ByteArrayInputStream(_notes[i].getBytes(StandardCharsets.UTF_8));
                noteBuffered = new BufferedInputStream(inputStream, BUFFER);

                ZipEntry entry = new ZipEntry(_notes[i]);
                out.putNextEntry(entry);
                int count;

                while ((count = noteBuffered.read(data, 0, BUFFER)) != -1) {
                    out.write(data, 0, count);
                }
                noteBuffered.close();
            }

            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getStoragePermission() {
        int hasStoragePermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasStoragePermission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE_ASK_PERMISSIONS);
            return;
        }
    }

    private void shareZipFile(String zipFileName) {
        File exportedNotesFile = new File("file://"+ Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + zipFileName);
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_FROM_STORAGE, exportedNotesFile);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Sharing exported notes");
        sendIntent.setType("application/zip");
        startActivity(Intent.createChooser(sendIntent, "share " + zipFileName));
        return;
    }
}

