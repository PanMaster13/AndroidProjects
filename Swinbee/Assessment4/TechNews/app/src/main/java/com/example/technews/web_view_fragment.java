package com.example.technews;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.graphics.Bitmap;
import android.widget.ProgressBar;

public class web_view_fragment extends Fragment {
    private static final String ARG_PARAM1 = "urlParam";

    private String url;
    private Boolean isFirstTimeLoad = true;
    private WebView webView;
    private ProgressBar spinner;

    public web_view_fragment() {
        // Required empty public constructor
    }

    public static web_view_fragment newInstance(String url) {
        web_view_fragment fragment = new web_view_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            url = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.web_view_fragment, container, false);
        webView = view.findViewById(R.id.webView);
        spinner = view.findViewById(R.id.progressBar1);
        webView.setWebViewClient(new CustomWebViewClient());

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setOverScrollMode(WebView.OVER_SCROLL_NEVER);
        webView.loadUrl(url);

        return view;
    }

    private class CustomWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if (isFirstTimeLoad){
                view.setVisibility(view.INVISIBLE);
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            isFirstTimeLoad = false;
            spinner.setVisibility(View.GONE);

            view.setVisibility(view.VISIBLE);
            super.onPageFinished(view, url);
        }
    }
}
