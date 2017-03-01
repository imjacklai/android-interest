package tw.ctl.interest.calculation;

import java.math.BigDecimal;
import java.text.NumberFormat;

import tw.ctl.interest.BasePresenter;

/**
 * Created by jacklai on 2017/3/1.
 */

public class CalculationPresenter implements BasePresenter<CalculationView> {

    private CalculationView view;

    @Override
    public void attachView(CalculationView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }

    public void calculate(String principal, String interest, String period, String invest) {
        BigDecimal principalDecimal = new BigDecimal(principal);
        BigDecimal interestDecimal = new BigDecimal(String.valueOf(Double.valueOf(interest) / 100));
        BigDecimal periodDecimal = new BigDecimal(period);
        int periodInt = Integer.valueOf(period);

        String simpleResult = calculateSimpleInterest(principalDecimal, interestDecimal, periodDecimal);
        String compoundResult = calculateCompoundInterest(principalDecimal, interestDecimal, periodInt);

        if (invest.isEmpty()) {
            view.onResult(simpleResult, compoundResult, "---");
        } else {
            BigDecimal investDecimal = new BigDecimal(invest);
            String investResult = calculateInvestInterest(principalDecimal, interestDecimal, periodInt, investDecimal);
            view.onResult(simpleResult, compoundResult, investResult);
        }
    }

    private String calculateSimpleInterest(BigDecimal principal, BigDecimal interest, BigDecimal period) {
        return formatDecimal(principal.add(principal.multiply(interest.multiply(period))));
    }

    private String calculateCompoundInterest(BigDecimal principal, BigDecimal interest, int period) {
        return formatDecimal(principal.multiply(BigDecimal.ONE.add(interest).pow(period)));
    }

    private String calculateInvestInterest(BigDecimal principal, BigDecimal interest, int period, BigDecimal invest) {
        BigDecimal result = principal;
        for (int i = 0; i < period; i++) {
            result = result.multiply(BigDecimal.ONE.add(interest)).add(invest);
        }
        return formatDecimal(result);
    }

    private String formatDecimal(BigDecimal decimal) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(2);
        numberFormat.setMinimumFractionDigits(2);
        return numberFormat.format(decimal);
    }

}
