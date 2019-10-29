package com.example.week_8_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final int THUMBNAIL_SIZE = 250;
    public static String KEY = "IMAGE";
    private static final String URL[] = {"https://i.redd.it/ker3idld27d21.png", "https://i.redd.it/7jajbgxfvav31.jpg", "https://i.redd.it/4j85v99s9av31.jpg"};

    private ImageView imageViews[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageViews = new ImageView[]{findViewById(R.id.imageView),
        findViewById(R.id.imageView2), findViewById(R.id.imageView3)};

        for (int c = 0; c < imageViews.length; c++){
            LoadImage(URL[c], c);
            imageViews[c].setOnClickListener(this);
            imageViews[c].setTag(c);
        }
    }

    private void LoadImage(String url, final int c){
        ImageLoader imageLoader = MySingleton.getInstance(getApplicationContext())
                .getImageLoader();
        imageLoader.get(url, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                Bitmap image = response.getBitmap();
                Bitmap thumbnail = ThumbnailUtils.extractThumbnail(image,
                        THUMBNAIL_SIZE, THUMBNAIL_SIZE);
                imageViews[c].setImageBitmap(thumbnail);
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getMessage());
            }
        });
    }

    @Override
    public void onClick(View view) {
        int pos = Integer.parseInt(view.getTag().toString());
        Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
        intent.putExtra(KEY, URL[pos]);
        startActivity(intent);
    }
}
