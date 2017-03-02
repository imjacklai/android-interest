package tw.ctl.interest.calculation;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tw.ctl.interest.Entity;
import tw.ctl.interest.R;

/**
 * Created by jacklai on 2017/3/1.
 */

public class CalculationFragment extends Fragment implements CalculationView {

    @BindView(R.id.scroll_view)              ScrollView scrollView;
    @BindView(R.id.card_view)                CardView cardView;
    @BindView(R.id.simple_interest_result)   TextView simpleResultText;
    @BindView(R.id.compound_interest_result) TextView compoundResultText;
    @BindView(R.id.invest_interest_result)   TextView investResultText;
    @BindView(R.id.principal_field)          EditText principalField;
    @BindView(R.id.interest_field)           EditText interestField;
    @BindView(R.id.period_field)             EditText periodField;
    @BindView(R.id.invest_field)             EditText investField;
    @BindView(R.id.ad_view)                  AdView adView;

    private CalculationPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculation, container, false);
        ButterKnife.bind(this, view);

        presenter = new CalculationPresenter();
        presenter.attachView(this);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    @Override
    public void onResult(Entity entity) {
        setAdView();

        simpleResultText.setText(entity.simpleResult);
        compoundResultText.setText(entity.compoundResult);
        investResultText.setText(entity.investResult);

        cardView.setVisibility(View.VISIBLE);

        scrollView.smoothScrollTo(0, 0);

        entity.save();
    }

    @OnClick(R.id.calculate_button)
    public void onCalculateButtonClicked() {
        Entity entity = new Entity();
        entity.principal = principalField.getText().toString();
        entity.interest = interestField.getText().toString();
        entity.period = periodField.getText().toString();
        entity.invest = investField.getText().toString();

        if (!checkFieldsValid(entity)) return;

        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager =
                    (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(
                    view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }

        principalField.clearFocus();
        interestField.clearFocus();
        periodField.clearFocus();
        investField.clearFocus();

        presenter.calculate(entity);
    }

    @OnClick(R.id.clear_button)
    public void onClearButtonClicked() {
        principalField.setText("");
        interestField.setText("");
        periodField.setText("");
        investField.setText("");
    }

    private boolean checkFieldsValid(Entity entity) {
        if (!entity.principal.isEmpty() && !entity.interest.isEmpty() && !entity.period.isEmpty()) {
            return true;
        } else {
            if (entity.principal.isEmpty()) principalField.setError("請輸入");
            if (entity.interest.isEmpty()) interestField.setError("請輸入");
            if (entity.period.isEmpty()) periodField.setError("請輸入");
            return false;
        }
    }

    private void setAdView() {
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                adView.setVisibility(View.VISIBLE);
            }
        });
        adView.loadAd(new AdRequest.Builder().build());
    }

}
