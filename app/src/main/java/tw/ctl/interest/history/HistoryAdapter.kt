package tw.ctl.interest.history

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_record.view.*
import tw.ctl.interest.R
import tw.ctl.interest.model.Record

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    private var records = listOf<Record>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_record, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bindRecord(records[position])
    }

    override fun getItemCount(): Int = records.size

    fun setRecords(records: List<Record>) {
        this.records = records
        notifyDataSetChanged()
    }

    class HistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindRecord(record: Record) {
            with(itemView) {
                simpleInterest.text = record.simpleResult
                compoundInterest.text = record.compoundResult
                investInterest.text = record.investResult
                principal.text = record.principal
                interest.text = record.interest
                period.text = record.period
                invest.text = record.invest

                val periodString = context.resources.getStringArray(R.array.period_array)[record.periodType]
                periodLabel.text = context.getString(R.string.period_with_type, "(${periodString})")
                investLabel.text = context.getString(R.string.invest, periodString, "")
            }
        }
    }

}
