package fr.trackoe.decheterie.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import fr.trackoe.decheterie.model.bean.global.CarteActive;

/**
 * Created by Haocheng on 06/04/2017.
 */

public class DchCarteActiveDB extends MyDb {

    public DchCarteActiveDB(Context ctx) {
        mydb = new DecheterieDatabase(ctx);
    }

    /*
    Insérer un CarteAvtive dans la bdd
     */
    public long insertCarteActive(CarteActive carteActive) {
        ContentValues values = new ContentValues();
        values.put(DecheterieDatabase.TableDchCarteActive.DCH_CARTE_ID, carteActive.getDchCarteId());
        values.put(DecheterieDatabase.TableDchCarteActive.DATE_ACTIVATION, carteActive.getDateActivation());
        values.put(DecheterieDatabase.TableDchCarteActive.DATE_DERNIER_MOTIF, carteActive.getDateDernierMotif());
        values.put(DecheterieDatabase.TableDchCarteActive.DCH_CARTE_ETAT_RAISON_ID, carteActive.getDchCarteEtatRaisonId());
        values.put(DecheterieDatabase.TableDchCarteActive.IS_ACTIVE, carteActive.isActive()? 1 : 0);
        values.put(DecheterieDatabase.TableDchCarteActive.DCH_COMPTE_PREPAYE_ID, carteActive.getDchComptePrepayeId());

        return db.insertOrThrow(DecheterieDatabase.TableDchCarteActive.TABLE_DCH_CARTE_ACTIVE, null, values);
    }

    public CarteActive getCarteActiveFromDchCarteId(long dchCarteId) {
        String query = "SELECT * FROM " + DecheterieDatabase.TableDchCarteActive.TABLE_DCH_CARTE_ACTIVE + " WHERE " + DecheterieDatabase.TableDchCarteActive.DCH_CARTE_ID + " = " + dchCarteId;
        Cursor cursor = db.rawQuery(query, null);
        return cursorToCarteActive(cursor);
    }

    public CarteActive cursorToCarteActive(Cursor c){
        CarteActive ca = new CarteActive();
        if(c.moveToFirst()) {
            ca.setDchCarteId(c.getLong(DecheterieDatabase.TableDchCarteActive.NUM_DCH_CARTE_ID));
            ca.setDateActivation(c.getString(DecheterieDatabase.TableDchCarteActive.NUM_DATE_ACTIVATION));
            ca.setDateDernierMotif(c.getString(DecheterieDatabase.TableDchCarteActive.NUM_DATE_DERNIER_MOTIF));
            ca.setDchCarteEtatRaisonId(c.getInt(DecheterieDatabase.TableDchCarteActive.NUM_DCH_CARTE_ETAT_RAISON_ID));
            ca.setActive((c.getInt(DecheterieDatabase.TableDchCarteActive.NUM_IS_ACTIVE) == 1)? true : false);
            ca.setDchComptePrepayeId(c.getLong(DecheterieDatabase.TableDchCarteActive.NUM_DCH_COMPTE_PREPAYE_ID));
            return ca;
        }
        else{
            return null;
        }

    }

    /*
    Vider la table
     */

    public void clearCarteActive() {
        db.execSQL("delete from " + DecheterieDatabase.TableDchCarteActive.TABLE_DCH_CARTE_ACTIVE);
    }

}
