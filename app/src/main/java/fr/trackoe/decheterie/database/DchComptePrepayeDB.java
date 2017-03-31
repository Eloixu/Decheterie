package fr.trackoe.decheterie.database;

import android.content.ContentValues;
import android.content.Context;

import fr.trackoe.decheterie.model.bean.global.ComptePrepaye;

/**
 * Created by Trackoe on 27/03/2017.
 */

public class DchComptePrepayeDB extends MyDb {
    public DchComptePrepayeDB(Context ctx) {
        mydb = new DecheterieDatabase(ctx);
    }

    /*
    Insérer un ComptePrepaye dans la bdd
     */
    public long insertComptePrepaye(ComptePrepaye comptePrepaye) {
        ContentValues values = new ContentValues();

        return db.insertOrThrow(DecheterieDatabase.TableDchComptePrepaye.TABLE_DCH_COMPTE_PREPAYE, null, values);
    }

    /*
    Vider la table
     */

    public void clearComptePrepaye() {
        db.execSQL("delete from " + DecheterieDatabase.TableDchComptePrepaye.TABLE_DCH_COMPTE_PREPAYE);
    }


}
