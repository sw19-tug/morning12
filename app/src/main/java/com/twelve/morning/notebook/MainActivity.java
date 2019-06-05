package com.twelve.morning.notebook;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.io.BufferedInputStream;

import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;



public class MainActivity extends AppCompatActivity {

    private ListView list_view;
    public NotesListAdapter adapter = null;
    private Sorting sorting = Sorting.CREATION;

    boolean firstLaunch = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseWrapper.getInstance().createDatabase(getApplicationContext());


        if(firstLaunch){
            firstLaunch = false;
            Locale locale = new Locale("de");
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getResources().updateConfiguration(
                    config,
                    getResources().getDisplayMetrics()
            );
        }
        setContentView(R.layout.activity_main);
        setupButtons();
        setupSearch();
        reloadNotes(sorting);

    }

    public void setupSearch() {
        SearchView sv = findViewById(R.id.search_view_find_text);
        sv.setSubmitButtonEnabled(true);
        sv.setIconifiedByDefault(false);
        //sv.setQueryHint("");
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                reloadNotesByText(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                reloadNotesByText(newText);
                return true;
            }
        });
    }

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
        Intent intent = getIntent();
        switch (item.getItemId()){
            case R.id.bt_import:
                showFileChooser();
                return true;
            case R.id.bt_export:
                ShareManager.getStoragePermission(this);
                String zipFileName = "exported_notes.zip";
                Note[] notesToExport = DatabaseWrapper.getInstance().getNotes(Sorting.TITLE);
                ShareManager.zip(notesToExport, zipFileName);
                startActivity(ShareManager.shareZipFile(zipFileName));
                return true;
            case R.id.bt_local:
                String[] languages = {"English", "Deutsch", "Hrvatski", "Italiano", "Español", "Deitsch"};
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Select Language");
                builder.setItems(languages, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String language = "en";
                        switch (which){
                            case 0: language = "en"; break;
                            case 1: language = "de"; break;
                            case 2: language = "hr"; break;
                            case 3: language = "it"; break;
                            case 4: language = "es"; break;
                            case 5: language = "st"; break;
                        }
                        LocaleHelper.setLocale(MainActivity.this, language);
                        recreate();
                    }
                });
                builder.show();
                return true;
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
    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/zip");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File"),0);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(this, "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0){
            String path = data.getData().getPath();
            path = path.substring(path.indexOf(':') + 1);
            unpackZip(path);
        }
    }

    private boolean unpackZip(String path)
    {
        InputStream is;
        ZipInputStream zis;
        try
        {
            String filename;
            is = new FileInputStream(path);
            zis = new ZipInputStream(new BufferedInputStream(is));
            ZipEntry ze;

            while ((ze = zis.getNextEntry()) != null)
            {
                filename = ze.getName();

                byte[] buffer = new byte[1024];
                int read = 0;
                String content = "";
                while ((read = zis.read(buffer)) != -1) {
                    content += new String(buffer);
                }

                Note note = new Note();
                note.setTitle(filename.replace(".txt", ""));
                note.setBody(content);
                DatabaseWrapper.getInstance().addNote(note);
                zis.closeEntry();
            }

            zis.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
            return false;
        }

        return true;
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

    public void reloadNotesByText(String query) {
        this.adapter = new NotesListAdapter(DatabaseWrapper.getInstance().getNotesByText(query), this);
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

        Button sort_size_button = findViewById(R.id.bt_sort_by_size);
        sort_size_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sorting = Sorting.SIZE;
                reloadNotes(sorting);
            }
        });

        Button delete_notes_button = findViewById(R.id.bt_delete_notes);
        delete_notes_button.setVisibility(View.INVISIBLE);
        delete_notes_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Note> notes = new ArrayList<Note>(Arrays.asList(adapter.getNotes()));
                DatabaseWrapper.getInstance().deleteNotes(notes);

                //CheckBox cb_selected = findViewById(R.id.cb_selected);
                //findViewById(R.id.cb_selected).setVisibility(View.INVISIBLE);
                adapter.setCbSelectedVisibility(View.GONE);

                reloadNotes(sorting);
            }
        });
    }
}

