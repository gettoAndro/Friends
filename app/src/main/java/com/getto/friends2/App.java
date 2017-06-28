package com.getto.friends2;

import android.app.Application;
import org.greenrobot.greendao.database.Database;

/**
 * Created by Getto on 24.06.2017.
 */

public class App extends Application {

    /** A flag to show how easily you can switch from standard SQLite to the encrypted SQLCipher. */

    private DaoSession daoSession;
    @Override
    public void onCreate() {
        super.onCreate();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "users-db");
        Database db =  helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

}
