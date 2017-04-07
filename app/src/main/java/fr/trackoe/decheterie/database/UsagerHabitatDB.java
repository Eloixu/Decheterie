package fr.trackoe.decheterie.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import fr.trackoe.decheterie.model.bean.global.UsagerHabitat;

/**
 * Created by Haocheng on 07/04/2017.
 */

public class UsagerHabitatDB extends MyDb {
    public UsagerHabitatDB(Context ctx) {
        mydb = new DecheterieDatabase(ctx);
    }

    /*
    Insérer un UsagerHabitat dans la bdd
     */
    public long insertUsagerHabitat(UsagerHabitat usagerHabitat) {
        ContentValues values = new ContentValues();

        values.put(DecheterieDatabase.TableUsagerHabitat.ID_USAGER, usagerHabitat.getDchUsagerId());
        values.put(DecheterieDatabase.TableUsagerHabitat.ID_HABITAT, usagerHabitat.getHabitatId());

        return db.insertOrThrow(DecheterieDatabase.TableUsagerHabitat.TABLE_NAME, null, values);
    }

    /*
    Vider la table
     */

    public void clearUsagerHabitat() {
        db.execSQL("delete from " + DecheterieDatabase.TableUsagerHabitat.TABLE_NAME);
    }

    public UsagerHabitat getUsagerHabitatByUsagerId(int usagerId) {
        UsagerHabitat usagerHabitat;
        String query = "SELECT * FROM " + DecheterieDatabase.TableUsagerHabitat.TABLE_NAME + " WHERE " + DecheterieDatabase.TableUsagerHabitat.ID_USAGER + "=" + usagerId;
        Cursor cursor = db.rawQuery(query, null);
        usagerHabitat = cursorToUsagerHabitat(cursor);
        return usagerHabitat;
    }

    private UsagerHabitat cursorToUsagerHabitat(Cursor c){
        if(c.getCount() == 0) {
            return null;
        }
        c.moveToFirst();
        UsagerHabitat u = new UsagerHabitat();
        u.setDchUsagerId(c.getInt(DecheterieDatabase.TableUsagerHabitat.NUM_ID_USAGER));
        u.setDchUsagerId(c.getInt(DecheterieDatabase.TableUsagerHabitat.NUM_ID_USAGER));

        c.close();

        return u;
    }


}
