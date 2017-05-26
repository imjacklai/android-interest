package tw.ctl.interest.calculation

import java.math.BigDecimal
import java.text.NumberFormat

import tw.ctl.interest.BasePresenter
import tw.ctl.interest.Entity

/**
 * Created by jacklai on 2017/3/1.
 */

class CalculationPresenter : BasePresenter<CalculationView> {

    private var view: CalculationView? = null

    override fun attachView(view: CalculationView) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    fun calculate(entity: Entity) {
        val principalDecimal = BigDecimal(entity.principal)
        val interestDecimal = BigDecimal((java.lang.Double.valueOf(entity.interest)!! / 100).toString())
        val periodDecimal = BigDecimal(entity.period)
        val periodInt = Integer.valueOf(entity.period)!!

        entity.principal = formatDecimal(principalDecimal)
        entity.simpleResult = calculateSimpleInterest(principalDecimal, interestDecimal, periodDecimal)
        entity.compoundResult = calculateCompoundInterest(principalDecimal, interestDecimal, periodInt)

        if (entity.invest!!.isEmpty()) {
            entity.invest = "---"
            entity.investResult = "---"
        } else {
            val investDecimal = BigDecimal(entity.invest)
            entity.invest = formatDecimal(investDecimal)
            entity.investResult = calculateInvestInterest(principalDecimal, interestDecimal, periodInt, investDecimal)
        }

        view!!.onResult(entity)
    }

    private fun calculateSimpleInterest(principal: BigDecimal, interest: BigDecimal, period: BigDecimal): String {
        return formatDecimal(principal.add(principal.multiply(interest.multiply(period))))
    }

    private fun calculateCompoundInterest(principal: BigDecimal, interest: BigDecimal, period: Int): String {
        return formatDecimal(principal.multiply(BigDecimal.ONE.add(interest).pow(period)))
    }

    private fun calculateInvestInterest(principal: BigDecimal, interest: BigDecimal, period: Int, invest: BigDecimal): String {
        var result = principal
        for (i in 0..period - 1) {
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
