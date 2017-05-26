package tw.ctl.interest

import android.app.Application

import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * Created by jacklai on 2017/3/1.
 */

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        /** Setup Realm. */
        Realm.init(this)
        Realm.setDefaultConfiguration(RealmConfiguration.Builder().build())
    }

}
