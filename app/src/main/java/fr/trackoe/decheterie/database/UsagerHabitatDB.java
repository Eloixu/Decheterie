package fr.trackoe.decheterie.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import fr.trackoe.decheterie.model.bean.usager.UsagerHabitat;

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

    public void updateUsagerHabitat(UsagerHabitat usagerHabitat) {
        deleteUsagerHabitat(usagerHabitat.getDchUsagerId(),usagerHabitat.getHabitatId());
        insertUsagerHabitat(usagerHabitat);
    }

    /*
    Vider la table
     */

    public void clearUsagerHabitat() {
        db.execSQL("delete from " + DecheterieDatabase.TableUsagerHabitat.TABLE_NAME);
    }

    public void deleteUsagerHabitat(int usagerId, int habitatId){
        db.execSQL("delete from " + DecheterieDatabase.TableUsagerHabitat.TABLE_NAME + " WHERE " + DecheterieDatabase.TableUsagerHabitat.ID_USAGER + "=" + usagerId + " AND " + DecheterieDatabase.TableUsagerHabitat.ID_HABITAT + "=" + habitatId);
    }

    public void deleteAllUsagerHabitatByUsagerId(int usagerId){
        db.execSQL("delete from " + DecheterieDatabase.TableUsagerHabitat.TABLE_NAME + " WHERE " + DecheterieDatabase.TableUsagerHabitat.ID_USAGER + "=" + usagerId);
    }

    public UsagerHabitat getUsagerHabitatByUsagerId(int usagerId) {
        UsagerHabitat usagerHabitat;
        String query = "SELECT * FROM " + DecheterieDatabase.TableUsagerHabitat.TABLE_NAME + " WHERE " + DecheterieDatabase.TableUsagerHabitat.ID_USAGER + "=" + usagerId;
        Cursor cursor = db.rawQuery(query, null);
        usagerHabitat = cursorToUsagerHabitat(cursor);
        return usagerHabitat;
    }

    public UsagerHabitat getUsagerHabitatByHabitatActiveId(int habitatActiveId) {
        UsagerHabitat usagerHabitat;
        String query = "SELECT * FROM " + DecheterieDatabase.TableUsagerHabitat.TABLE_NAME + " WHERE " + DecheterieDatabase.TableUsagerHabitat.ID_HABITAT + "=" + habitatActiveId;
        Cursor cursor = db.rawQuery(query, null);
        usagerHabitat = cursorToUsagerHabitat(cursor);
        return usagerHabitat;
    }

    public UsagerHabitat getUsagerHabitatByUsagerIdAndHabitatId(int usagerId, int habitatId) {
        UsagerHabitat usagerHabitat;
        String query = "SELECT * FROM " + DecheterieDatabase.TableUsagerHabitat.TABLE_NAME + " WHERE " + DecheterieDatabase.TableUsagerHabitat.ID_USAGER + "=" + usagerId + " AND " + DecheterieDatabase.TableUsagerHabitat.ID_HABITAT + "=" + habitatId;
        Cursor cursor = db.rawQuery(query, null);
        usagerHabitat = cursorToUsagerHabitat(cursor);
        return usagerHabitat;
    }

    public ArrayList<UsagerHabitat> getListUsagerHabitatByUsagerId(int usagerId) {
        ArrayList<UsagerHabitat> usagerHabitatList;
        String query = "SELECT * FROM " + DecheterieDatabase.TableUsagerHabitat.TABLE_NAME + " WHERE " + DecheterieDatabase.TableUsagerHabitat.ID_USAGER + "=" + usagerId;
        Cursor cursor = db.rawQuery(query, null);
        usagerHabitatList = cursorToListUsagerHabitat(cursor);
        return usagerHabitatList;
    }

    private UsagerHabitat cursorToUsagerHabitat(Cursor c){
        UsagerHabitat u = new UsagerHabitat();
        try {
            if (c.moveToFirst()) {
                u.setDchUsagerId(c.getInt(DecheterieDatabase.TableUsagerHabitat.NUM_ID_USAGER));
                u.setHabitatId(c.getInt(DecheterieDatabase.TableUsagerHabitat.NUM_ID_HABITAT));
                c.close();
                return u;
            } else {
                c.close();
                return null;
            }
        }catch(Exception e){
            return u;
        }

    }

    private ArrayList<UsagerHabitat> cursorToListUsagerHabitat(Cursor c) {
        ArrayList<UsagerHabitat> usagerHabitatList = new ArrayList<>();

        try {
            if (c.moveToFirst()) {
                do {
                    UsagerHabitat u = new UsagerHabitat();
                    u.setDchUsagerId(c.getInt(DecheterieDatabase.TableUsagerHabitat.NUM_ID_USAGER));
                    u.setHabitatId(c.getInt(DecheterieDatabase.TableUsagerHabitat.NUM_ID_HABITAT));
                    usagerHabitatList.add(u);
                } while (c.moveToNext());

                c.close();
            }
            return usagerHabitatList;
        }catch(Exception e){
            return usagerHabitatList;
        }
    }


}
