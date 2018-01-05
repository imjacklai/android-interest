package tw.ctl.interest.history

import io.realm.Realm
import io.realm.Sort
import tw.ctl.interest.Entity

class HistoryPresenter {

    private var view: HistoryView? = null
    private val realm = Realm.getDefaultInstance()

    fun attachView(view: HistoryView) {
        this.view = view
    }

    fun detachView() {
        view = null
        realm.close()
    }

    fun fetchLocalData() {
        val entities = realm.where<Entity>(Entity::class.java).sort("date", Sort.DESCENDING).findAll()

        entities.addChangeListener { elements ->
            if (elements.size > 20) elements.last()?.delete()
            view?.onHistories(elements)
        }

        view?.onHistories(entities)
    }

}
