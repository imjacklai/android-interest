package tw.ctl.interest.history

import io.realm.RealmResults
import tw.ctl.interest.Entity

interface HistoryView {
    fun onHistories(histories: RealmResults<Entity>)
}
