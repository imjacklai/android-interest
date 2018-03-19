package tw.ctl.interest.history

import tw.ctl.interest.model.Record

interface HistoryView {
    fun onFetchRecords(records: List<Record>)
}
