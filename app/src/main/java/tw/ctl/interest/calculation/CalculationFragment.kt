package tw.ctl.interest.calculation

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.fragment_calculation.*
import tw.ctl.interest.Entity
import tw.ctl.interest.R

/**
 * Created by jacklai on 2017/3/1.
 */

class CalculationFragment : Fragment(), CalculationView {

    private val presenter = CalculationPresenter()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_calculation, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.attachView(this)

        calculateButton.setOnClickListener { onCalculateButtonClicked() }
        clearButton.setOnClickListener { onClearButtonClicked() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
        adView.destroy()
    }

    override fun onResult(entity: Entity) {
        setAdView()

        simpleInterestResult.text = entity.simpleResult
        compoundInterestResult.text = entity.compoundResult
        investInterestResult.text = entity.investResult

        cardView.visibility = View.VISIBLE

        scrollView.smoothScrollTo(0, 0)

        entity.save()
    }

    fun onCalculateButtonClicked() {
        val entity = Entity()
        entity.principal = principalField.text.toString()
        entity.interest = interestField.text.toString()
        entity.period = periodField.text.toString()
        entity.invest = investField.text.toString()

        if (!checkFieldsValid(entity)) return

        val view = activity.currentFocus
        if (view != null) {
            val inputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }

        principalField.clearFocus()
        interestField.clearFocus()
        periodField.clearFocus()
        investField.clearFocus()

        presenter.calculate(entity)
    }

    fun onClearButtonClicked() {
        principalField.setText("")
        interestField.setText("")
        periodField.setText("")
        investField.setText("")
    }

    private fun checkFieldsValid(entity: Entity): Boolean {
        if (!entity.principal!!.isEmpty() && !entity.interest!!.isEmpty() && !entity.period!!.isEmpty()) {
            return true
        } else {
            if (entity.principal!!.isEmpty()) principalField.error = "請輸入"
            if (entity.interest!!.isEmpty()) interestField.error = "請輸入"
            if (entity.period!!.isEmpty()) periodField.error = "請輸入"
            return false
        }
    }

    private fun setAdView() {
        adView.adListener = object : AdListener() {
            override fun onAdLoaded() {
                adView.visibility = View.VISIBLE
            }
        }
        adView.loadAd(AdRequest.Builder().build())
    }

}