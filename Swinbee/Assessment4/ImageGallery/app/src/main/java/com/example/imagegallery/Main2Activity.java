package com.example.imagegallery;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

public class Main2Activity extends AppCompatActivity {

    ImageView enlargedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        enlargedImage = findViewById(R.id.enlargedImage);

        Bundle bundle = getIntent().getExtras();
        String url = bundle.getString("URL");
        LoadImage(url);
    }

    private void LoadImage(String url){
        ImageLoader imageLoader = MySingleton.getInstance(getApplicationContext()).getImageLoader();

        imageLoader.get(url, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                Bitmap bitmap = response.getBitmap();
                enlargedImage.setImageBitmap(bitmap);
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }
}
