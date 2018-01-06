package tw.ctl.interest

import io.realm.DynamicRealm
import io.realm.RealmMigration

class Migration : RealmMigration {

    override fun migrate(realm: DynamicRealm?, oldVersion: Long, newVersion: Long) {
        // DynamicRealm exposes an editable schema
        val schema = realm?.schema

        if (oldVersion == 0L) {
            schema?.get(Entity::class.java.simpleName)?.run {
                setRequired(Entity::principal.name, true)
                setRequired(Entity::interest.name, true)
                setRequired(Entity::period.name, true)
                setRequired(Entity::invest.name, true)
                setRequired(Entity::simpleResult.name, true)
                setRequired(Entity::compoundResult.name, true)
                setRequired(Entity::investResult.name, true)
            }
        }
    }

}
