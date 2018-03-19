package tw.ctl.interest.calculation

import tw.ctl.interest.model.Record
import java.math.BigDecimal
import java.text.NumberFormat

class CalculationPresenter {

    private var view: CalculationView? = null

    fun attachView(view: CalculationView) {
        this.view = view
    }

    fun detachView() {
        view = null
    }

    fun calculate(record: Record) {
        val principalDecimal = BigDecimal(record.principal)
        val interestDecimal = BigDecimal((record.interest.toDouble() / 100).toString())
        val periodDecimal = BigDecimal(record.period)
        val periodInt = record.period.toInt()

        record.principal = formatDecimal(principalDecimal)
        record.simpleResult = calculateSimpleInterest(principalDecimal, interestDecimal, periodDecimal)
        record.compoundResult = calculateCompoundInterest(principalDecimal, interestDecimal, periodInt)

        if (record.invest.isEmpty()) {
            record.invest = "---"
            record.investResult = "---"
        } else {
            val investDecimal = BigDecimal(record.invest)
            record.invest = formatDecimal(investDecimal)
            record.investResult = calculateInvestInterest(principalDecimal, interestDecimal, periodInt, investDecimal)
        }

        view?.onResult(record)
    }

    private fun calculateSimpleInterest(principal: BigDecimal, interest: BigDecimal, period: BigDecimal): String
        = formatDecimal(principal.add(principal.multiply(interest.multiply(period))))

    private fun calculateCompoundInterest(principal: BigDecimal, interest: BigDecimal, period: Int): String
        = formatDecimal(principal.multiply(BigDecimal.ONE.add(interest).pow(period)))

    private fun calculateInvestInterest(principal: BigDecimal, interest: BigDecimal, period: Int, invest: BigDecimal): String {
        var result = principal
        for (i in 0 until period) {
            result = result.multiply(BigDecimal.ONE.add(interest)).add(invest)
        }
        return formatDecimal(result)
    }

    private fun formatDecimal(decimal: BigDecimal): String {
        val numberFormat = NumberFormat.getNumberInstance()
        numberFormat.maximumFractionDigits = 2
        numberFormat.minimumFractionDigits = 2
        return numberFormat.format(decimal)
    }

}
