package tw.ctl.interest.history

import tw.ctl.interest.model.Record

interface HistoryView {
    fun onFetchSuccess(records: List<Record>)
    fun onFetchFailure(error: Throwable)
}
