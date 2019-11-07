package com.example.technews;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class cyber_fragment extends Fragment{
    private StaggeredGridLayoutManager staggeredGridLayoutManager;

    private static String PARAM_KEY = "URL_PATH";
    private CyberFragmentInteractionListener mListener;

    public cyber_fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tabview_fragment, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);

        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        final List<NewsObject> list = getListItemData();

        MyRecycleViewAdapter.AdapterCallBack listener = new MyRecycleViewAdapter.AdapterCallBack() {
            @Override
            public void onMethodCallBack(int position) {
                onButtonPressed(list.get(position).getUrl());
            }
        };

        MyRecycleViewAdapter rcAdapter = new MyRecycleViewAdapter(list, listener);
        recyclerView.setAdapter(rcAdapter);

        return view;
    }

    private List<NewsObject> getListItemData(){
        List<NewsObject> newsObjectList = new ArrayList<NewsObject>();
        int counter = 1;
        List<String> newsItemList = new ArrayList<String>();
        BufferedReader reader;

        try{
            final InputStream file = getActivity().getAssets().open("news_items.txt");
            reader = new BufferedReader(new InputStreamReader(file));
            String line = reader.readLine();
            while (line != null) {
                newsItemList.add(line);
                line = reader.readLine();
                counter++;
                if (counter > 5){
                    String category = removeColonFromText(newsItemList.get(0));
                    if (category.contains("cybersecurity")){
                        String imageName = removeWord(removeColonFromText(newsItemList.get(1)), ".png");
                        String title = removeColonFromText(newsItemList.get(2));
                        String urlLine[] = newsItemList.get(3).split(":");
                        String url = urlLine[1] + ":" + urlLine[2];

                        Resources resources = getResources();
                        int imageResourceId = resources.getIdentifier(imageName, "drawable", getActivity().getPackageName());
                        newsObjectList.add(new NewsObject(imageResourceId, category, title, url));
                    }
                    counter = 1;
                    newsItemList.clear();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return newsObjectList;
    }

    private String removeColonFromText(String text){
        String textLine[] = text.split(":");
        String textValue = textLine[1];
        return textValue;
    }

    // Deals with removal of text
    public String removeWord(String string, String text){
        if (string.contains(text)){
            string = string.replaceAll(text, "");
        }
        return string;
    }

    public void onButtonPressed(String url){
        if (mListener != null){
            mListener.CyberFragmentInteraction(url);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CyberFragmentInteractionListener){
            mListener = (CyberFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement CyberFragmentInteractionListener!");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface CyberFragmentInteractionListener {
        void CyberFragmentInteraction(String url);
    }
}
