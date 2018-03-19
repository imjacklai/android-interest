package tw.ctl.interest.history

import android.content.Context
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import tw.ctl.interest.model.RecordDatabase

class HistoryPresenter {

    private var view: HistoryView? = null

    fun attachView(view: HistoryView) {
        this.view = view
    }

    fun detachView() {
        view = null
    }

    fun fetchLocalData(context: Context) {
        RecordDatabase.getInstance(context)?.recordDao()?.getRecords()!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { records -> view?.onFetchRecords(records) },
                        { error -> Log.e("asd", error.message) }
                )
    }

}
