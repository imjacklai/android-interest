package tw.ctl.interest.calculation

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.fragment_calculation.*
import tw.ctl.interest.R
import tw.ctl.interest.model.Record

class CalculationFragment : Fragment(), CalculationView, AdapterView.OnItemSelectedListener {

    private val presenter = CalculationPresenter()

    private var periodType: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = inflater.inflate(R.layout.fragment_calculation, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)

        val adapter = ArrayAdapter.createFromResource(context, R.array.period_array, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = this

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
        presenter.save(context, record)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        periodType = position
        val periodString = parent?.getItemAtPosition(position).toString()
        investInputLayout.hint = getString(R.string.invest, periodString, getString(R.string.optional))
    }

    private fun onCalculateButtonClicked() {
        val record = Record(
                principalField.text.toString(),
                interestField.text.toString(),
                periodType,
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

        try {
            presenter.calculate(record)
        } catch (e: NumberFormatException) {
            periodField.error = "請輸入合理週期"
        }
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
            if (record.principal.isEmpty()) principalField.error = getString(R.string.please_enter)
            if (record.interest.isEmpty()) interestField.error = getString(R.string.please_enter)
            if (record.period.isEmpty()) periodField.error = getString(R.string.please_enter)
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
