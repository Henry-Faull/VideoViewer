package com.henry.videoviewer;

import android.content.Intent;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity {
    private Intent requestVideoFileIntent;
    private Intent requestImageFileIntent;
    private ParcelFileDescriptor parcelFileDescriptor;
    public static final String INTENT_MESSAGE = "filepath";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestVideoFileIntent = new Intent(Intent.ACTION_PICK);
        requestVideoFileIntent.setType("video/mp4");
        requestImageFileIntent = new Intent(Intent.ACTION_PICK);
        requestImageFileIntent.setType("image/png");
        Log.i("progress", "onCreate");
    }

    //sends Intent for the video file (talk to Gallery app)
    public void fetchVideo(View view) {
        Log.i("progress", "fetchVideo");

        startActivityForResult(requestVideoFileIntent,0);
    }

    public void fetchImage(View view) {

        Log.i("progress" , "fetchImage");
        startActivityForResult(requestImageFileIntent,0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent returnIntent) {
        // test if selection worked
        Log.i("progress", "onActivityResult");
        if (resultCode != RESULT_OK) {
            return;
        } else {

            // get URI
            Uri returnUri = returnIntent.getData();
            try {
                parcelFileDescriptor = getContentResolver().openFileDescriptor(returnUri, "r");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Log.e("MainActivity", "File not found.");
                return;
            }

            if (getContentResolver().getType(returnUri).equals("video/mp4")) {
                goToVideoViewer(returnUri);
            } else {
                gotToImageViewer(returnUri);
            }
        }
    }

    //sends Intent to transition to viewing screen
    public void goToVideoViewer(Uri vidURI) {
        Log.i("progress","goToViewer");

        Intent playerIntent  = new Intent(this, VideoActivity.class);

        //attaches the video's uri
        playerIntent.putExtra(INTENT_MESSAGE, vidURI.toString());
        startActivity(playerIntent);
    }

    public void gotToImageViewer(Uri imgURI) {
        Log.i("progress" , "goToDisplay");

        Intent displayIntent = new Intent(this, ImageActivity.class);

        //attaches the image's uri
        displayIntent.putExtra(INTENT_MESSAGE, imgURI.toString());
        startActivity(displayIntent);
    }

}