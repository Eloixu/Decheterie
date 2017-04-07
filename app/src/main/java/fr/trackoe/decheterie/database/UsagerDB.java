package fr.trackoe.decheterie.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

import fr.trackoe.decheterie.model.bean.global.Module;
import fr.trackoe.decheterie.model.bean.global.Modules;
import fr.trackoe.decheterie.model.bean.usager.Usager;

/**
 * Created by Remi on 05/04/2017.
 */
public class UsagerDB extends MyDb {

    public UsagerDB(Context ctx) {
        mydb = new DecheterieDatabase(ctx);
    }

    /*
    Insérer un Usager dans la bdd
     */
    public long insertUsager(Usager usager) {
        ContentValues values = new ContentValues();
        values.put(DecheterieDatabase.TableUsager.ID_USAGER, usager.getId());
        values.put(DecheterieDatabase.TableUsager.ID_ACCOUNT, usager.getIdAccount());
        values.put(DecheterieDatabase.TableUsager.NOM, usager.getNom());
        values.put(DecheterieDatabase.TableUsager.DATE_MAJ, usager.getDateMaj());

        return db.insertOrThrow(DecheterieDatabase.TableUsager.TABLE_NAME, null, values);
    }

    public Usager getUsagerFromID(int id) {
        String query = "SELECT * FROM " + DecheterieDatabase.TableUsager.TABLE_NAME + " WHERE " + DecheterieDatabase.TableUsager.ID_USAGER + " = " + id;
        Cursor cursor = db.rawQuery(query, null);
        return cursorToUsager(cursor);
    }

    public Usager cursorToUsager(Cursor c){
        Usager u = new Usager();
        if(c.moveToFirst()) {
            u.setId(c.getInt(DecheterieDatabase.TableUsager.NUM_ID_USAGER));
            u.setIdAccount(c.getInt(DecheterieDatabase.TableUsager.NUM_ID_ACCOUNT));
            u.setNom(c.getString(DecheterieDatabase.TableUsager.NUM_NOM));
            u.setDateMaj(c.getString(DecheterieDatabase.TableUsager.NUM_DATE_MAJ));
        }
        return u;
    }

    /*
    Vider la table
     */

    public void clearUsager() {
        db.execSQL("delete from " + DecheterieDatabase.TableUsager.TABLE_NAME);
    }

}
