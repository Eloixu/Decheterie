package fr.trackoe.decheterie.widget;

import android.content.Context;
import android.os.AsyncTask;

import fr.trackoe.decheterie.database.UsersDB;
import fr.trackoe.decheterie.model.bean.global.User;
import fr.trackoe.decheterie.model.bean.global.Users;

/**
 * Created by Remi on 06/10/2016.
 */
public class WriteUsersTask extends AsyncTask {
    private Context ctx;
    private Users users;

    public WriteUsersTask(Context ctx, Users users) {
        this.ctx = ctx;
        this.users = users;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        UsersDB usersDB = new UsersDB(ctx);
        usersDB.open();
        usersDB.clearUsers();
        for (User u : users.getListUsers()) {
            usersDB.insertUser(u);
        }
        usersDB.close();
        return null;
    }
}
