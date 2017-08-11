package fr.trackoe.decheterie.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import fr.trackoe.decheterie.model.bean.global.Ville;

/**
 * Created by Remi on 18/05/2016.
 */
public class VillesDB extends MyDb {

    public VillesDB(Context ctx) {
        mydb = new DecheterieDatabase(ctx);
    }

    /*
    Insérer une ville dans la bdd
     */
    public long insertVille(Ville ville) {
        ContentValues values = new ContentValues();

        values.put(DecheterieDatabase.TableVille.ID, ville.getIdVille());
        values.put(DecheterieDatabase.TableVille.NOM, ville.getNom());
        values.put(DecheterieDatabase.TableVille.CP, ville.getCp());

        return db.insertOrThrow(DecheterieDatabase.TableVille.TABLE_VILLE, null, values);
    }

    /*
    Vider la table
     */

    public void clearVilles() {
        db.execSQL("delete from " + DecheterieDatabase.TableVille.TABLE_VILLE);
    }

    public ArrayList<Ville> getListeVilles() {
        ArrayList<Ville> listeVilles;
        String query = "SELECT * FROM " + DecheterieDatabase.TableVille.TABLE_VILLE + ";";
        Cursor cursor = db.rawQuery(query, null);
        listeVilles = cursorToListeVilles(cursor);
        return listeVilles;
    }

    private ArrayList<Ville> cursorToListeVilles(Cursor c) {
        ArrayList<Ville> listeVilles = new ArrayList<>();

        if (c.moveToFirst()) {
            do {
                Ville v = new Ville();
                v.setIdVille(c.getInt(DecheterieDatabase.TableVille.NUM_ID));
                v.setNom(c.getString(DecheterieDatabase.TableVille.NUM_NOM));
                v.setCp(c.getString(DecheterieDatabase.TableVille.NUM_CP));

                listeVilles.add(v);

            } while (c.moveToNext());

            c.close();
        }
        return listeVilles;
    }
}
