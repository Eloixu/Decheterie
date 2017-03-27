package fr.trackoe.decheterie.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import fr.trackoe.decheterie.model.bean.global.DecheterieFlux;
import fr.trackoe.decheterie.model.bean.global.Flux;

/**
 * Created by Haocheng on 24/03/2017.
 */

public class DchDecheterieFluxDB extends MyDb {
    public DchDecheterieFluxDB(Context ctx) {
        mydb = new DecheterieDatabase(ctx);
    }

    /*
    Insérer un decheterie_flux dans la bdd
     */
    public long insertDecheterieFlux(DecheterieFlux decheterieFlux) {
        ContentValues values = new ContentValues();

        values.put(DecheterieDatabase.TableDchDecheterieFlux.DCH_DECHETERIE_ID, decheterieFlux.getDecheterieId());
        values.put(DecheterieDatabase.TableDchDecheterieFlux.DCH_FLUX_ID, decheterieFlux.getFluxId());

        return db.insertOrThrow(DecheterieDatabase.TableDchDecheterieFlux.TABLE_DCH_DECHETERIE_FLUX, null, values);
    }

    /*
    Vider la table
     */

    public void clearDecheterieFlux() {
        db.execSQL("delete from " + DecheterieDatabase.TableDchDecheterieFlux.TABLE_DCH_DECHETERIE_FLUX);
    }

    public ArrayList<Flux> getAllFluxByDecheterieId(int decheterieId, Context ctx) {
        ArrayList<Flux> fluxList;
        String query = "SELECT * FROM " + DecheterieDatabase.TableDchDecheterieFlux.TABLE_DCH_DECHETERIE_FLUX + " WHERE " + DecheterieDatabase.TableDchDecheterieFlux.DCH_DECHETERIE_ID + "=" + decheterieId + ";";
        Cursor cursor = db.rawQuery(query, null);
        fluxList = cursorToListeFlux(cursor,ctx);
        return fluxList;
    }

    private ArrayList<Flux> cursorToListeFlux(Cursor c, Context ctx) {
        ArrayList<Flux> fluxList = new ArrayList<>();
        DchFluxDB dchFluxDB = new DchFluxDB(ctx);;
        dchFluxDB.open();
        if (c.moveToFirst()) {
            do {
                int fluxId = c.getInt(DecheterieDatabase.TableDchDecheterieFlux.NUM_DCH_FLUX_ID);
                Flux f = dchFluxDB.getFluxByIdentifiant(fluxId);
                fluxList.add(f);
            } while (c.moveToNext());

            c.close();
        }
        dchFluxDB.close();
        return fluxList;
    }
}
