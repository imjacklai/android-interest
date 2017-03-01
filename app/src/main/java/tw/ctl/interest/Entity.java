package tw.ctl.interest;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmObject;

/**
 * Created by jacklai on 2017/3/1.
 */

public class Entity extends RealmObject {

    public String principal;
    public String interest;
    public String period;
    public String invest;

    public String simpleResult;
    public String compoundResult;
    public String investResult;

    public Date date;

    public Entity() {}

    public void save() {
        date = new Date();
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealm(this);
        realm.commitTransaction();
        realm.close();
    }

    public void delete() {
        Realm realm = Realm.getDefaultInstance();
        Entity entity = realm.where(Entity.class).equalTo("date", date).findFirst();
        realm.beginTransaction();
        entity.deleteFromRealm();
        realm.commitTransaction();
        realm.close();
    }

}
