package com.example.depremradari;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class TatbikatVideo extends Fragment {

    private CardView[] navCards;
    private String[] videos;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_video, container, false);

        navCards = new CardView[]{
                rootView.findViewById(R.id.card_1),
                rootView.findViewById(R.id.card_2),
                rootView.findViewById(R.id.card_3),
                rootView.findViewById(R.id.card_4),
                rootView.findViewById(R.id.card_5),
                rootView.findViewById(R.id.card_6),
                rootView.findViewById(R.id.card_7),
                rootView.findViewById(R.id.card_8),
                rootView.findViewById(R.id.card_9),
                rootView.findViewById(R.id.card_10),
        };
        videos = new String[]{
                "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/mgsZC5XLwHQ?si=13aB0EyuHEtcOPD9\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" allowfullscreen></iframe>",
                "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/brSdfPnN0tg?si=wwLhSOri_hJYYw7w\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" allowfullscreen></iframe>",
                "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/xJ5tO8S2uzE?si=g3TLshrUJpFeZCz8\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" allowfullscreen></iframe>",
                "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/8ckk-o1QkWw?si=AWPzDBivC_2S3-BJ\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" allowfullscreen></iframe>",
                "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/I_reIHQrcNY?si=KKtQtSo0rYx_uhZW\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" allowfullscreen></iframe>",
                "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/oZeI0X40EEY?si=qdl87c6I2XNZQFqx\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" allowfullscreen></iframe>",
                "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/WapaOFFWee0?si=w2YfHcDyPs7lvPcI\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" allowfullscreen></iframe>",
                "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/G1sHBXX88GI?si=q5ANzh9-UIWYRUIX\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" allowfullscreen></iframe>",
                "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/K0keerAalYE?si=o8b2czb5EPDwC-nn\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" allowfullscreen></iframe>",
                "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/zG9CG2BK4BU?si=xL33pvF5gwUoIoWN\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" allowfullscreen></iframe>",
        };

        Toolbar toolbar = rootView.findViewById(R.id.toolbar);

        if (getActivity() instanceof AppCompatActivity) {

            toolbar.setNavigationOnClickListener(view -> {
                // Fragment'tan önceki ekrana dön
                if (getFragmentManager() != null) {
                    getFragmentManager().popBackStack(); // BackStack'i yönet
                }
            });
        }

        for (int i = 0; i < navCards.length; i++) {

            int finalI = i;
            // Lambda ifadesi ile tüm kartlara OnClickListener ata
            navCards[i].setOnClickListener(v -> openNewFragmentOrActivity(finalI));
        }

        return rootView;

    }

    private void openNewFragmentOrActivity(int cardIndex) {
        if (cardIndex < videos.length) {
            String videoUrl = videos[cardIndex];
            Fragment fragment = TatbikatVideoView.newInstance(videoUrl);
            navigateToFragment(fragment);
        }
    }

    private void navigateToFragment(Fragment fragment) {
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayoutTatbikat, fragment)
                .addToBackStack(null)
                .commit();
    }
}