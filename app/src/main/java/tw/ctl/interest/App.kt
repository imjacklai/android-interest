package tw.ctl.interest

import android.app.Application

import io.realm.Realm
import io.realm.RealmConfiguration

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        /** Setup Realm. */
        Realm.init(this)
        Realm.setDefaultConfiguration(RealmConfiguration.Builder()
                .schemaVersion(1)
                .migration(Migration())
                .build())
    }

}
