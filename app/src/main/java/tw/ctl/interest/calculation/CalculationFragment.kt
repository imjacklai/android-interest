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
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_calculation.*
import tw.ctl.interest.R
import tw.ctl.interest.model.Record
import tw.ctl.interest.model.RecordDatabase
import java.util.*

class CalculationFragment : Fragment(), CalculationView {

    private val presenter = CalculationPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = inflater.inflate(R.layout.fragment_calculation, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)
        calculateButton.setOnClickListener { onCalculateButtonClicked() }
        clearButton.setOnClickListener { onClearButtonClicked() }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
        adView?.destroy()
    }

    override fun onResult(record: Record) {
        setAdView()
        simpleInterestResult.text = record.simpleResult
        compoundInterestResult.text = record.compoundResult
        investInterestResult.text = record.investResult
        cardView.visibility = View.VISIBLE
        scrollView.smoothScrollTo(0, 0)

        Completable.fromAction {
            record.date = Date()
            RecordDatabase.getInstance(context!!)?.recordDao()?.insert(record)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {  }
    }

    private fun onCalculateButtonClicked() {
        val record = Record(
                principalField.text.toString(),
                interestField.text.toString(),
                periodField.text.toString(),
                investField.text.toString()
        )

        if (!checkFieldsValid(record)) return

        val view = activity?.currentFocus
        if (view != null) {
            val inputMethodManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }

        principalField.clearFocus()
        interestField.clearFocus()
        periodField.clearFocus()
        investField.clearFocus()

        presenter.calculate(record)
    }

    private fun onClearButtonClicked() {
        principalField.setText("")
        interestField.setText("")
        periodField.setText("")
        investField.setText("")
    }

    private fun checkFieldsValid(record: Record): Boolean {
        return if (record.principal.isNotEmpty() && record.interest.isNotEmpty() && record.period.isNotEmpty()) {
            true
        } else {
            if (record.principal.isEmpty()) principalField.error = "請輸入"
            if (record.interest.isEmpty()) interestField.error = "請輸入"
            if (record.period.isEmpty()) periodField.error = "請輸入"
            false
        }
    }

    private fun setAdView() {
        adView?.adListener = object : AdListener() {
            override fun onAdLoaded() {
                adView?.visibility = View.VISIBLE
            }
        }
        adView?.loadAd(AdRequest.Builder().build())
    }

}
