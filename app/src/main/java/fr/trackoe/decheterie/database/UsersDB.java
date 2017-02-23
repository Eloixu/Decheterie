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
        mydb = new FormulairesDatabase(ctx);
    }

    /*
    Insérer un User dans la bdd
     */
    public long insertUser(User user) {
        ContentValues values = new ContentValues();

        values.put(FormulairesDatabase.TableUsers.ID_USER, Integer.parseInt(user.getIdUser()));
        values.put(FormulairesDatabase.TableUsers.LOGIN, user.getLogin());
        values.put(FormulairesDatabase.TableUsers.PASSWORD, user.getPassword());
        values.put(FormulairesDatabase.TableUsers.NOM, user.getNom());
        values.put(FormulairesDatabase.TableUsers.PRENOM, user.getPrenom());
        values.put(FormulairesDatabase.TableUsers.AUTORISATION_CHANGEMENT_INTER, user.isAutorisationChangementInter() ? 1 : 0);

        return db.insertOrThrow(FormulairesDatabase.TableUsers.TABLE_USERS, null, values);
    }

    /*
    Vider la table
     */

    public void clearUsers() {
        db.execSQL("delete from " + FormulairesDatabase.TableUsers.TABLE_USERS);
    }

    public User getUserByIdentifiant(String login) {
        User user;
        String query = "SELECT * FROM " + FormulairesDatabase.TableUsers.TABLE_USERS + " WHERE " + FormulairesDatabase.TableUsers.LOGIN + "='" + login + "';";
        Cursor cursor = db.rawQuery(query, null);
        user = cursorToUser(cursor);
        return user;
    }

    public boolean isUserAutorisationChangementBac(int idUser) {
        User user;
        String query = "SELECT * FROM " + FormulairesDatabase.TableUsers.TABLE_USERS + " WHERE " + FormulairesDatabase.TableUsers.ID_USER + "=" + idUser + ";";
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
        u.setIdUser(String.valueOf(c.getInt(FormulairesDatabase.TableUsers.NUM_ID_USER)));
        u.setLogin(c.getString(FormulairesDatabase.TableUsers.NUM_LOGIN));
        u.setPassword(c.getString(FormulairesDatabase.TableUsers.NUM_PASSWORD));
        u.setNom(c.getString(FormulairesDatabase.TableUsers.NUM_NOM));
        u.setPrenom(c.getString(FormulairesDatabase.TableUsers.NUM_PRENOM));
        u.setIsAutorisationChangementInter(c.getInt(FormulairesDatabase.TableUsers.NUM_AUTORISATION_CHANGEMENT_INTER) != 0);

        c.close();

        return u;
    }
}
