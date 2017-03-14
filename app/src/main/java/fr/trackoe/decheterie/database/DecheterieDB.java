package fr.trackoe.decheterie.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import fr.trackoe.decheterie.model.bean.global.Decheterie;


/**
 * Created by Haocheng on 14/03/2017.
 */
public class DecheterieDB extends MyDb {

    public DecheterieDB(Context ctx) {
        mydb = new DecheterieDatabase(ctx);
    }

    /*
    Insérer un Decheterie dans la bdd
     */
    public long insertDecheterie(Decheterie decheterie) {
        ContentValues values = new ContentValues();

        values.put(DecheterieDatabase.TableDecheterie.ID_ACCOUNT, decheterie.getIdAccount());
        values.put(DecheterieDatabase.TableDecheterie.NAME, decheterie.getName());
        values.put(DecheterieDatabase.TableDecheterie.CONSIGNE_COMPTAGE, decheterie.getConsigneComptage());
        values.put(DecheterieDatabase.TableDecheterie.CONSIGNE_SIGNATURE, decheterie.getConsigneSignature());
        values.put(DecheterieDatabase.TableDecheterie.APPORT, decheterie.isApport()? 1 : 0);
        values.put(DecheterieDatabase.TableDecheterie.UNITE_TOTAL, decheterie.getUniteTotal());

        return db.insertOrThrow(DecheterieDatabase.TableDecheterie.TABLE_DECHETERIE, null, values);
    }

    /*
    Vider la table
     */

    public void clearDecheterie() {
        db.execSQL("delete from " + DecheterieDatabase.TableDecheterie.TABLE_DECHETERIE);
    }

    public Decheterie getDecheterieByIdentifiant(String idAccount) {
        Decheterie decheterie;
        String query = "SELECT * FROM " + DecheterieDatabase.TableDecheterie.TABLE_DECHETERIE + " WHERE " + DecheterieDatabase.TableDecheterie.ID_ACCOUNT + "='" + idAccount + "';";
        Cursor cursor = db.rawQuery(query, null);
        decheterie = cursorToDechetrie(cursor);
        return decheterie;
    }

    public ArrayList<Decheterie> getAllDecheteries(String idAccount) {
        ArrayList<Decheterie> decheterieList;
        String query = "SELECT * FROM " + DecheterieDatabase.TableDecheterie.TABLE_DECHETERIE;
        Cursor cursor = db.rawQuery(query, null);
        decheterieList = cursorToListeDechetrie(cursor);
        return decheterieList;
    }


    private Decheterie cursorToDechetrie(Cursor c){
        if(c.getCount() == 0) {
            return null;
        }
        c.moveToFirst();
        Decheterie d = new Decheterie();
        d.setIdAccount(c.getInt(DecheterieDatabase.TableDecheterie.NUM_ID_ACCOUNT));
        d.setName(c.getString(DecheterieDatabase.TableDecheterie.NUM_NAME));
        d.setConsigneComptage(c.getString(DecheterieDatabase.TableDecheterie.NUM_CONSIGNE_COMPTAGE));
        d.setConsigneSignature(c.getString(DecheterieDatabase.TableDecheterie.NUM_CONSIGNE_SIGNATURE));
        d.setApport((c.getInt(DecheterieDatabase.TableDecheterie.NUM_APPORT) == 1)? true : false);
        d.setUniteTotal(c.getString(DecheterieDatabase.TableDecheterie.NUM_UNITE_TOTAL));

        c.close();

        return d;
    }

    private ArrayList<Decheterie> cursorToListeDechetrie(Cursor c) {
        ArrayList<Decheterie> decheterieList = new ArrayList<>();

        if (c.moveToFirst()) {
            do {
                Decheterie d = new Decheterie();
                d.setIdAccount(c.getInt(DecheterieDatabase.TableDecheterie.NUM_ID_ACCOUNT));
                d.setName(c.getString(DecheterieDatabase.TableDecheterie.NUM_NAME));
                d.setConsigneComptage(c.getString(DecheterieDatabase.TableDecheterie.NUM_CONSIGNE_COMPTAGE));
                d.setConsigneSignature(c.getString(DecheterieDatabase.TableDecheterie.NUM_CONSIGNE_SIGNATURE));
                d.setApport((c.getInt(DecheterieDatabase.TableDecheterie.NUM_APPORT) == 1)? true : false);
                d.setUniteTotal(c.getString(DecheterieDatabase.TableDecheterie.NUM_UNITE_TOTAL));
                decheterieList.add(d);
            } while (c.moveToNext());

            c.close();
        }
        return decheterieList;
    }
}
