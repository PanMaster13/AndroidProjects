package com.example.imagegallery;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private final int THUMBNAIL_SIZE = 250;
    public static String KEY = "IMAGE";
    public static final String URL = "http://3.0.148.2/ayaya1.png";
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);

        LoadImage(URL);
        imageView.setOnClickListener(this);
    }

    private void LoadImage(String url){
        ImageLoader imageLoader = MySingleton.getInstance(getApplicationContext())
                .getImageLoader();
        imageLoader.get(url, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                Bitmap image = response.getBitmap();
                Bitmap thumnail = ThumbnailUtils.extractThumbnail(image, THUMBNAIL_SIZE, THUMBNAIL_SIZE);
                imageView.setImageBitmap(thumnail);
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getMessage());
            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}
