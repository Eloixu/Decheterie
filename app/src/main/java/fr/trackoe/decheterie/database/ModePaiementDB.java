package fr.trackoe.decheterie.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;


import fr.trackoe.decheterie.model.bean.global.ModePaiement;

/**
 * Created by Haocheng on 30/05/2017.
 */

public class ModePaiementDB extends MyDb {

    public ModePaiementDB(Context ctx) {
        mydb = new DecheterieDatabase(ctx);
    }

    /*
    Insérer un ModePaiement dans la bdd
     */
    public long insertModePaiement(ModePaiement modePaiement) {
        ContentValues values = new ContentValues();

        values.put(DecheterieDatabase.TableModePaiement.ID, modePaiement.getId());
        values.put(DecheterieDatabase.TableModePaiement.MODE, modePaiement.getMode());

        return db.insertOrThrow(DecheterieDatabase.TableModePaiement.TABLE_NAME, null, values);
    }

    public void updateModePaiement(ModePaiement modePaiement) {
        ContentValues values = new ContentValues();

        values.put(DecheterieDatabase.TableModePaiement.ID, modePaiement.getId());
        values.put(DecheterieDatabase.TableModePaiement.MODE, modePaiement.getMode());

        db.update(DecheterieDatabase.TableModePaiement.TABLE_NAME, values,DecheterieDatabase.TableModePaiement.ID + "=" + modePaiement.getId(),null);
    }

    /*
    Vider la table
     */

    public void clearModePaiement() {
        db.execSQL("delete from " + DecheterieDatabase.TableModePaiement.TABLE_NAME);
    }

    public ModePaiement getModePaiementByIdentifiant(int id) {
        ModePaiement modePaiement;
        String query = "SELECT * FROM " + DecheterieDatabase.TableModePaiement.TABLE_NAME + " WHERE " + DecheterieDatabase.TableModePaiement.ID + "=" + id + ";";
        Cursor cursor = db.rawQuery(query, null);
        modePaiement = cursorToModePaiement(cursor);
        return modePaiement;
    }



    private ModePaiement cursorToModePaiement(Cursor c){
        if(c.moveToFirst()) {
            ModePaiement m = new ModePaiement();
            m.setId(c.getInt(DecheterieDatabase.TableModePaiement.NUM_ID));
            m.setMode(c.getString(DecheterieDatabase.TableModePaiement.NUM_MODE));

            c.close();

            return m;
        }
        else{
            return null;
        }
    }

}
