package fr.trackoe.decheterie.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import fr.trackoe.decheterie.model.bean.global.Rue;

/**
 * Created by Remi on 18/05/2016.
 */
public class RuesDB extends MyDb {

    public RuesDB(Context ctx) {
        mydb = new FormulairesDatabase(ctx);
    }

    /*
    Insérer une rue dans la bdd
     */
    public long insertRue(Rue rue) {
        ContentValues values = new ContentValues();

        values.put(FormulairesDatabase.TableRue.ID, rue.getIdRue());
        values.put(FormulairesDatabase.TableRue.NOM, rue.getNom());
        values.put(FormulairesDatabase.TableRue.ID_VILLE, rue.getIdVille());

        return db.insertOrThrow(FormulairesDatabase.TableRue.TABLE_RUE, null, values);
    }

    /*
    Vider la table
     */

    public void clearRues() {
        db.execSQL("delete from " + FormulairesDatabase.TableRue.TABLE_RUE);
    }

    public ArrayList<Rue> getListeRues() {
        ArrayList<Rue> listeRues;
        String query = "SELECT * FROM " + FormulairesDatabase.TableRue.TABLE_RUE + ";";
        Cursor cursor = db.rawQuery(query, null);
        listeRues = cursorToListeRues(cursor);
        return listeRues;
    }

    private ArrayList<Rue> cursorToListeRues(Cursor c) {
        ArrayList<Rue> listeRues = new ArrayList<>();

        if (c.moveToFirst()) {
            do {
                Rue r = new Rue();
                r.setIdRue(c.getInt(FormulairesDatabase.TableRue.NUM_ID));
                r.setNom(c.getString(FormulairesDatabase.TableRue.NUM_NOM));
                r.setIdVille(c.getInt(FormulairesDatabase.TableRue.NUM_ID_VILLE));
                listeRues.add(r);

            } while (c.moveToNext());

            c.close();
        }
        return listeRues;
    }

    public ArrayList<String> getListeNomRuesString(int idVille) {
        ArrayList<String> listeNomsRue = new ArrayList<>();
        String query = "SELECT * FROM " + FormulairesDatabase.TableRue.TABLE_RUE + " WHERE " + FormulairesDatabase.TableRue.ID_VILLE + " = " + idVille + ";";
        Cursor cursor = db.rawQuery(query, null);
        listeNomsRue = cursorToListeNomRues(cursor);
        return listeNomsRue;
    }

    private ArrayList<String> cursorToListeNomRues(Cursor c) {
        ArrayList<String> listeRues = new ArrayList<>();

        if (c.moveToFirst()) {
            do {
                listeRues.add(c.getString(FormulairesDatabase.TableRue.NUM_NOM));
            } while (c.moveToNext());
            c.close();
        }
        return listeRues;
    }
}
