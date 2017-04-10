package fr.trackoe.decheterie.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import fr.trackoe.decheterie.model.bean.global.CarteEtatRaison;

/**
 * Created by Haocheng on 10/04/2017.
 */

public class DchCarteEtatRaison extends MyDb{

    public DchCarteEtatRaison(Context ctx) {
        mydb = new DecheterieDatabase(ctx);
    }

    /*
    Insérer un CarteEtatRaison dans la bdd
     */
    public long insertCarteEtatRaison(CarteEtatRaison carteEtatRaison) {
        ContentValues values = new ContentValues();
        //values.put(DecheterieDatabase.TableDchCarteEtatRaison.ID, carteEtatRaison.getId());
        values.put(DecheterieDatabase.TableDchCarteEtatRaison.RAISON, carteEtatRaison.getRaison());

        return db.insertOrThrow(DecheterieDatabase.TableDchCarteEtatRaison.TABLE_NAME, null, values);
    }

    public CarteEtatRaison getCarteEtatRaisonFromID(int id) {
        String query = "SELECT * FROM " + DecheterieDatabase.TableDchCarteEtatRaison.TABLE_NAME + " WHERE " + DecheterieDatabase.TableDchCarteEtatRaison.ID + " = " + id;
        Cursor cursor = db.rawQuery(query, null);
        return cursorToCarteEtatRaison(cursor);
    }

    public CarteEtatRaison cursorToCarteEtatRaison(Cursor c){
        CarteEtatRaison cer = new CarteEtatRaison();
        if(c.moveToFirst()) {
            cer.setId(c.getInt(DecheterieDatabase.TableDchCarteEtatRaison.NUM_ID));
            cer.setRaison(c.getString(DecheterieDatabase.TableDchCarteEtatRaison.NUM_RAISON));
        }
        return cer;
    }

    /*
    Vider la table
     */

    public void clearCarteEtatRaison() {
        db.execSQL("delete from " + DecheterieDatabase.TableDchCarteEtatRaison.TABLE_NAME);
    }

}
