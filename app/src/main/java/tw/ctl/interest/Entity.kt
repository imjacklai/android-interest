package tw.ctl.interest

import io.realm.Realm
import io.realm.RealmObject
import java.util.*

/**
 * Created by jacklai on 2017/5/26.
 */

annotation class PoKo

@PoKo data class Entity(
        var principal: String? = null,
        var interest: String? = null,
        var period: String? = null,
        var invest: String? = null,
        var simpleResult: String? = null,
        var compoundResult: String? = null,
        var investResult: String? = null,
        var date: Date? = null
) : RealmObject() {

    fun save() {
        date = Date()
        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        realm.copyToRealm(this)
        realm.commitTransaction()
        realm.close()
    }

    fun delete() {
        if (!this.isValid) return
        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        deleteFromRealm()
        realm.commitTransaction()
        realm.close()
    }

}