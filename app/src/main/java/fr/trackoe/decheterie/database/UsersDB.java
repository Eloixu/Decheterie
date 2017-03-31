package fr.trackoe.decheterie.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import fr.trackoe.decheterie.model.bean.global.User;

/**
 * Created by Remi on 02/12/2015.
 */
public class UsersDB extends MyDb {

    public  UsersDB(Context ctx) {
        mydb = new DecheterieDatabase(ctx);
    }

    /*
    Insérer un User dans la bdd
     */
    public long insertUser(User user) {
        ContentValues values = new ContentValues();

        values.put(DecheterieDatabase.TableUsers.ID_USER, Integer.parseInt(user.getIdUser()));
        values.put(DecheterieDatabase.TableUsers.LOGIN, user.getLogin());
        values.put(DecheterieDatabase.TableUsers.PASSWORD, user.getPassword());
        values.put(DecheterieDatabase.TableUsers.NOM, user.getNom());
        values.put(DecheterieDatabase.TableUsers.PRENOM, user.getPrenom());
        values.put(DecheterieDatabase.TableUsers.AUTORISATION_CHANGEMENT_INTER, user.isAutorisationChangementInter() ? 1 : 0);

        return db.insertOrThrow(DecheterieDatabase.TableUsers.TABLE_USERS, null, values);
    }

    /*
    Vider la table
     */

    public void clearUsers() {
        db.execSQL("delete from " + DecheterieDatabase.TableUsers.TABLE_USERS);
    }

    public User getUserByIdentifiant(String login) {
        User user;
        String query = "SELECT * FROM " + DecheterieDatabase.TableUsers.TABLE_USERS + " WHERE " + DecheterieDatabase.TableUsers.LOGIN + "='" + login + "';";
        Cursor cursor = db.rawQuery(query, null);
        user = cursorToUser(cursor);
        return user;
    }

    public boolean isUserAutorisationChangementBac(int idUser) {
        User user;
        String query = "SELECT * FROM " + DecheterieDatabase.TableUsers.TABLE_USERS + " WHERE " + DecheterieDatabase.TableUsers.ID_USER + "=" + idUser + ";";
        Cursor cursor = db.rawQuery(query, null);
        user = cursorToUser(cursor);
        return user.isAutorisationChangementInter();
    }

    private User cursorToUser(Cursor c){
        if(c.getCount() == 0) {
            return null;
        }
        c.moveToFirst();
        User u = new User();
        u.setIdUser(String.valueOf(c.getInt(DecheterieDatabase.TableUsers.NUM_ID_USER)));
        u.setLogin(c.getString(DecheterieDatabase.TableUsers.NUM_LOGIN));
        u.setPassword(c.getString(DecheterieDatabase.TableUsers.NUM_PASSWORD));
        u.setNom(c.getString(DecheterieDatabase.TableUsers.NUM_NOM));
        u.setPrenom(c.getString(DecheterieDatabase.TableUsers.NUM_PRENOM));
        u.setIsAutorisationChangementInter(c.getInt(DecheterieDatabase.TableUsers.NUM_AUTORISATION_CHANGEMENT_INTER) != 0);

        c.close();

        return u;
    }
}
