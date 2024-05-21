package com.example.depremradari;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

public class TatbikatVideoView extends Fragment{

    private static final String ARG_VIDEO_URL = "video_url";
    private String videoUrl;

    public static TatbikatVideoView newInstance(String videoUrl) {
        TatbikatVideoView fragment = new TatbikatVideoView();
        Bundle args = new Bundle();
        args.putString(ARG_VIDEO_URL, videoUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            videoUrl = getArguments().getString(ARG_VIDEO_URL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.video_layout, container, false);

        WebView webView = rootView.findViewById(R.id.Webview);
        Toolbar toolbar = rootView.findViewById(R.id.toolbarInfo);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        if (videoUrl != null) {
            webView.loadData(videoUrl, "text/html", "utf-8");
        }

        if (getActivity() instanceof AppCompatActivity) {

            toolbar.setNavigationOnClickListener(view -> {
                // Fragment'tan önceki ekrana dön
                if (getFragmentManager() != null) {
                    getFragmentManager().popBackStack(); // BackStack'i yönet
                }
            });
        }
        return rootView;

    }
}
