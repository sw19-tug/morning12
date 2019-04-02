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
import android.view.View;
import android.widget.ArrayAdapter;
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
    private String[] notes = new String[] {
            "Two plus two is four",
            "Minus one that's three, quick maths",
            "Everyday man's on the block",
            "Smoke trees (ah)",
            "See your girl in the park",
            "That girl is a uckers",
            "When the ting went quack-quack-quack",
            "You man were ducking (you man ducked)"
    };
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      
        switchToCreateNoteActivity();
        exportNotes();
      
        list_view = (ListView) findViewById(R.id.list_notes);

        ArrayAdapter<String> list_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, notes);
        list_view.setAdapter(list_adapter);
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

    private void exportNotes(){
        FloatingActionButton export_note_btn = findViewById(R.id.bt_export);
        export_note_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String zipFileName = "exported_notes.zip";
                zip(notes, zipFileName);
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

