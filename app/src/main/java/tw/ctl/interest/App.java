package tw.ctl.interest;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by jacklai on 2017/3/1.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        /** Setup Realm. */
        Realm.init(this);
        Realm.setDefaultConfiguration(new RealmConfiguration.Builder().build());
    }

}
