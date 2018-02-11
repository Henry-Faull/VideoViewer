package com.henry.videoviewer;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class ImageActivity extends AppCompatActivity {
    private Uri imageUri;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        Intent creatorIntent = getIntent();

        //set up video player and set the video path
        imageView = findViewById(R.id.imageView);
        String image = creatorIntent.getStringExtra("filepath");
        imageUri = Uri.parse(image);
        imageView.setImageURI(imageUri);

    }
}