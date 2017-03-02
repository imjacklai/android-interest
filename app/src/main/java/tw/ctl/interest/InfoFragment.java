package tw.ctl.interest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.NativeExpressAdView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jacklai on 2017/3/1.
 */

public class InfoFragment extends Fragment {

    @BindView(R.id.ad_card_view) CardView adCardView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        ButterKnife.bind(this, view);

        NativeExpressAdView adView = new NativeExpressAdView(getActivity());

        adCardView.addView(adView);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;

        float scale = getActivity().getResources().getDisplayMetrics().density;
        int adWidth = (int) ((screenWidth - (int) getResources().getDimension(R.dimen._20sdp)) / scale);

        adView.setAdSize(new AdSize(adWidth, 132));
        adView.setAdUnitId("ca-app-pub-2754939326809910/2852662581");

        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                adCardView.setVisibility(View.VISIBLE);
            }
        });

        adView.loadAd(new AdRequest.Builder().build());

        return view;
    }

}
