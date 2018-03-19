package tw.ctl.interest.calculation

import tw.ctl.interest.model.Record

interface CalculationView {
    fun onResult(record: Record)
}
