package com.twelve.morning.notebook;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ShareManager {

    static private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    static void zip(Note[] _notes, String zipFileName) {
        int BUFFER = 1000;
        try {
            BufferedInputStream noteBuffered = null;
            FileOutputStream dest = new FileOutputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + zipFileName);
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
            byte data[] = new byte[BUFFER];

            for (int i = 0; i < _notes.length; i++) {
                //FileInputStream fi = new FileInputStream(_files[i]);
                InputStream inputStream = new ByteArrayInputStream(_notes[i].getBody().getBytes(StandardCharsets.UTF_8));
                noteBuffered = new BufferedInputStream(inputStream, BUFFER);

                ZipEntry entry = new ZipEntry(_notes[i].getTitle() + ".txt");
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

    static Intent shareZipFile(String zipFileName) {
        File exportedNotesFile = new File("file://"+ Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + zipFileName);
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_FROM_STORAGE, exportedNotesFile);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Sharing exported notes");
        sendIntent.setType("application/zip");
        return Intent.createChooser(sendIntent, "share " + zipFileName);
    }

    static void getStoragePermission(Activity act) {
        int hasStoragePermission = act.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasStoragePermission != PackageManager.PERMISSION_GRANTED) {
            act.requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE_ASK_PERMISSIONS);
            return;
        }
    }
}
