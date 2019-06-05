package com.twelve.morning.notebook;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CreateNoteActivity extends AppCompatActivity {

    private Note last_saved_note = null;

    public Note getLastSavedNote() {
        return this.last_saved_note;
    }

    private FusedLocationProviderClient fusedLocationClient;

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 1234;

    private Boolean location_permission_granted = false;

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

        checkForLocationPermission();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        finishCreateNoteActivity((Button) findViewById(R.id.bt_note_create_cancel));
        finishCreateNoteActivity((Button) findViewById(R.id.bt_note_create_save));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    location_permission_granted = true;
                }
            }
        }
    }


    private void storeNote() {
        EditText title = CreateNoteActivity.this.findViewById(R.id.et_note_title);
        EditText body = CreateNoteActivity.this.findViewById(R.id.et_note_body);
        final String title_text = title.getText().toString();
        final String body_text = body.getText().toString();
        if (!(title_text.isEmpty() && body_text.isEmpty())) {
            last_saved_note = new Note(title_text, body_text);
        }

        String[] tags = TagManager.parse(body_text);
        List<Tag> Tags = new ArrayList<>();
        for (String tag1 : tags) {
            Tag tag = new Tag(tag1);
            Tags.add(tag);
        }


        final Note note_to_store = new Note(title_text, body_text);
        DatabaseWrapper.getInstance().addNote(note_to_store);
        final Integer target_id = DatabaseWrapper.getInstance().lastAddedNoteKey();

        updateLocationOnPermissionGranted(target_id);
    }


    @SuppressLint("MissingPermission")
    private void updateLocationOnPermissionGranted(final Integer target_id){
        if(location_permission_granted) {
            fusedLocationClient.getLastLocation().addOnSuccessListener(this,
                new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if(location != null) {
                            Note note_to_update = DatabaseWrapper.getInstance()
                                    .getNoteById(target_id);
                            if (note_to_update != null
                                    && note_to_update.getId() == target_id){

                                try {
                                    List<Address> addresses;
                                    Geocoder geocoder = new Geocoder(getApplicationContext(),
                                            Locale.getDefault());
                                    addresses = geocoder.getFromLocation(location.getLatitude(),
                                            location.getLongitude(), 1);
                                    note_to_update.setAddress(addresses.get(0).
                                            getAddressLine(0));
                                    System.out.println(location.toString());
                                    for (Address a: addresses){
                                        System.out.println(a.toString());
                                    }
                                    note_to_update.setLocation(location);
                                    DatabaseWrapper.getInstance().saveNote(note_to_update);
                                } catch(IOException ioe) {
                                    note_to_update.setLocation(location);
                                    DatabaseWrapper.getInstance().saveNote(note_to_update);
                                }

                            }

                        }
                    }

                });
        }

    }

    private void checkForLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);

        } else {
            location_permission_granted = true;
        }
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
