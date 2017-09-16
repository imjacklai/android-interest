package tw.ctl.interest.calculation

import java.math.BigDecimal
import java.text.NumberFormat

import tw.ctl.interest.Entity

class CalculationPresenter {

    private var view: CalculationView? = null

    fun attachView(view: CalculationView) {
        this.view = view
    }

    fun detachView() {
        view = null
    }

    fun calculate(entity: Entity) {
        val principalDecimal = BigDecimal(entity.principal)
        val interestDecimal = BigDecimal((entity.interest.toDouble() / 100).toString())
        val periodDecimal = BigDecimal(entity.period)
        val periodInt = entity.period.toInt()

        entity.principal = formatDecimal(principalDecimal)
        entity.simpleResult = calculateSimpleInterest(principalDecimal, interestDecimal, periodDecimal)
        entity.compoundResult = calculateCompoundInterest(principalDecimal, interestDecimal, periodInt)

        if (entity.invest.isEmpty()) {
            entity.invest = "---"
            entity.investResult = "---"
        } else {
            val investDecimal = BigDecimal(entity.invest)
            entity.invest = formatDecimal(investDecimal)
            entity.investResult = calculateInvestInterest(principalDecimal, interestDecimal, periodInt, investDecimal)
        }

        view?.onResult(entity)
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
