package fr.trackoe.decheterie.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import fr.trackoe.decheterie.model.bean.usager.UsagerMenage;

/**
 * Created by Haocheng on 10/04/2017.
 */

public class UsagerMenageDB extends MyDb {
    public UsagerMenageDB(Context ctx) {
        mydb = new DecheterieDatabase(ctx);
    }

    /*
    Insérer un UsagerMenage dans la bdd
     */
    public long insertUsagerMenage(UsagerMenage usagerMenage) {
        ContentValues values = new ContentValues();

        values.put(DecheterieDatabase.TableUsagerMenage.ID_USAGER, usagerMenage.getDchUsagerId());
        values.put(DecheterieDatabase.TableUsagerMenage.ID_MENAGE, usagerMenage.getMenageId());

        return db.insertOrThrow(DecheterieDatabase.TableUsagerMenage.TABLE_NAME, null, values);
    }

/*
Vider la table
 */

    public void clearUsagerMenage() {
        db.execSQL("delete from " + DecheterieDatabase.TableUsagerMenage.TABLE_NAME);
    }

    public UsagerMenage getUsagerMenageByUsagerId(int usagerId) {
        UsagerMenage usagerMenage;
        String query = "SELECT * FROM " + DecheterieDatabase.TableUsagerMenage.TABLE_NAME + " WHERE " + DecheterieDatabase.TableUsagerMenage.ID_USAGER + "=" + usagerId;
        Cursor cursor = db.rawQuery(query, null);
        usagerMenage = cursorToUsagerMenage(cursor);
        return usagerMenage;
    }

    private UsagerMenage cursorToUsagerMenage(Cursor c){
        if(c.getCount() == 0) {
            return null;
        }
        c.moveToFirst();
        UsagerMenage u = new UsagerMenage();
        u.setDchUsagerId(c.getInt(DecheterieDatabase.TableUsagerMenage.NUM_ID_USAGER));
        u.setMenageId(c.getInt(DecheterieDatabase.TableUsagerMenage.NUM_ID_MENAGE));

        c.close();

        return u;
    }


}

