package com.example.imagegallery;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

public class GridViewAdapter extends BaseAdapter {
    private Context context;
    private final int THUMBNAIL_SIZE = 250;
    // Localhost IP changes depending on device used
    public static final String URL[] = {"http://172.17.3.191/OTHERS/task2Images/image1.png",
            "http://172.17.3.191/OTHERS/task2Images/image2.png",
            "http://172.17.3.191/OTHERS/task2Images/image3.png",
            "http://172.17.3.191/OTHERS/task2Images/image4.png",
            "http://172.17.3.191/OTHERS/task2Images/image5.png",
            "http://172.17.3.191/OTHERS/task2Images/image6.png"};

    public GridViewAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return URL.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view == null){
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(R.layout.image_layout, null);
            ViewHolder viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }

        final ViewHolder viewHolder = (ViewHolder)view.getTag();
        ImageLoader imageLoader = MySingleton.getInstance(context).getImageLoader();

        imageLoader.get(URL[i], new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                Bitmap bitmap = response.getBitmap();
                Bitmap thumbnail = ThumbnailUtils.extractThumbnail(bitmap, THUMBNAIL_SIZE, THUMBNAIL_SIZE);
                viewHolder.imageView.setImageBitmap(thumbnail);
                viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, Main2Activity.class);
                        intent.putExtra("URL", URL[i]);
                        context.startActivity(intent);
                    }
                });
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        return view;
    }

    public class ViewHolder{

        public ImageView imageView;

        public ViewHolder(View view) {
            imageView = view.findViewById(R.id.imageView);
        }
    }
}
