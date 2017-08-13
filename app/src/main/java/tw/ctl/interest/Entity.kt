package tw.ctl.interest

import io.realm.Realm
import io.realm.RealmObject
import java.util.*

annotation class PoKo

@PoKo data class Entity(
        var principal: String,
        var interest: String,
        var period: String,
        var invest: String,
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