package fr.trackoe.decheterie.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import fr.trackoe.decheterie.model.bean.usager.Habitat;

/**
 * Created by Haocheng on 07/04/2017.
 */

public class HabitatDB extends MyDb {
    public HabitatDB(Context ctx) {
        mydb = new DecheterieDatabase(ctx);
    }

    /*
    Insérer un Habitat dans la bdd
     */
    public long insertHabitat(Habitat habitat) {
        ContentValues values = new ContentValues();
        values.put(DecheterieDatabase.TableHabitat.ID_HABITAT, habitat.getIdHabitat());
        values.put(DecheterieDatabase.TableHabitat.ADRESSE, habitat.getAdresse());
        values.put(DecheterieDatabase.TableHabitat.CP, habitat.getCp());
        values.put(DecheterieDatabase.TableHabitat.VILLE, habitat.getVille());
        values.put(DecheterieDatabase.TableHabitat.NB_LGT, habitat.getNbLgt());
        values.put(DecheterieDatabase.TableHabitat.NB_HABITANT, habitat.getNbHabitant());
        values.put(DecheterieDatabase.TableHabitat.NOM, habitat.getNom());
        values.put(DecheterieDatabase.TableHabitat.REFERENCE, habitat.getReference());
        values.put(DecheterieDatabase.TableHabitat.COORDONNEES_X, habitat.getCoordonneesX());
        values.put(DecheterieDatabase.TableHabitat.COORDONNEES_Y, habitat.getCoordonneesY());
        values.put(DecheterieDatabase.TableHabitat.COMPLEMENT, habitat.getComplement());
        values.put(DecheterieDatabase.TableHabitat.DERNIER_MAJ, habitat.getDernierMaj());
        values.put(DecheterieDatabase.TableHabitat.NUMERO, habitat.getNumero());
        values.put(DecheterieDatabase.TableHabitat.IS_ACTIF, habitat.isActif());
        values.put(DecheterieDatabase.TableHabitat.ACTIVITES, habitat.getActivites());
        values.put(DecheterieDatabase.TableHabitat.ADRESSE_2, habitat.getAdresse2());
        values.put(DecheterieDatabase.TableHabitat.REMARQUE, habitat.getRemarque());
        values.put(DecheterieDatabase.TableHabitat.ID_TYPE_HABITAT, habitat.getIdTypeHabitat());
        values.put(DecheterieDatabase.TableHabitat.ID_ACCOUNT, habitat.getIdAccount());
        return db.insertOrThrow(DecheterieDatabase.TableHabitat.TABLE_NAME, null, values);
    }

    public Habitat getHabitatFromID(int id) {
        String query = "SELECT * FROM " + DecheterieDatabase.TableHabitat.TABLE_NAME + " WHERE " + DecheterieDatabase.TableHabitat.ID_HABITAT + " = " + id;
        Cursor cursor = db.rawQuery(query, null);
        return cursorToHabitat(cursor);
    }

    public ArrayList<Habitat> getHabitatListByAdresse(String adresse) {
        ArrayList<Habitat> habitatList;
        String query = "SELECT * FROM " + DecheterieDatabase.TableHabitat.TABLE_NAME + " WHERE " + DecheterieDatabase.TableHabitat.ADRESSE  + " LIKE " + "'%" + adresse + "%'";
        Cursor cursor = db.rawQuery(query, null);
        habitatList = cursorToListeHabitat(cursor);
        return habitatList;
    }

    public ArrayList<Habitat> getHabitatListByCP(String cp) {
        ArrayList<Habitat> habitatList;
        String query = "SELECT * FROM " + DecheterieDatabase.TableHabitat.TABLE_NAME + " WHERE " + DecheterieDatabase.TableHabitat.CP  + " LIKE " + "'%" + cp + "%'";
        Cursor cursor = db.rawQuery(query, null);
        habitatList = cursorToListeHabitat(cursor);
        return habitatList;
    }

    public ArrayList<Habitat> getHabitatListByVille(String ville) {
        ArrayList<Habitat> habitatList;
        String query = "SELECT * FROM " + DecheterieDatabase.TableHabitat.TABLE_NAME + " WHERE " + DecheterieDatabase.TableHabitat.VILLE  + " LIKE " + "'%" + ville + "%'";
        Cursor cursor = db.rawQuery(query, null);
        habitatList = cursorToListeHabitat(cursor);
        return habitatList;
    }

    public ArrayList<Habitat> getHabitatListByComplement(String complement) {
        ArrayList<Habitat> habitatList;
        String query = "SELECT * FROM " + DecheterieDatabase.TableHabitat.TABLE_NAME + " WHERE " + DecheterieDatabase.TableHabitat.COMPLEMENT  + " LIKE " + "'%" + complement + "%'";
        Cursor cursor = db.rawQuery(query, null);
        habitatList = cursorToListeHabitat(cursor);
        return habitatList;
    }

    public ArrayList<Habitat> getHabitatListByNumero(String numero) {
        ArrayList<Habitat> habitatList;
        String query = "SELECT * FROM " + DecheterieDatabase.TableHabitat.TABLE_NAME + " WHERE " + DecheterieDatabase.TableHabitat.NUMERO  + " LIKE " + "'%" + numero + "%'";
        Cursor cursor = db.rawQuery(query, null);
        habitatList = cursorToListeHabitat(cursor);
        return habitatList;
    }

    public Habitat cursorToHabitat(Cursor c){
        Habitat h = new Habitat();
        try {
            if (c.moveToFirst()) {
                h.setIdHabitat(c.getInt(DecheterieDatabase.TableHabitat.NUM_ID_HABITAT));
                h.setAdresse(c.getString(DecheterieDatabase.TableHabitat.NUM_ADRESSE));
                h.setCp(c.getString(DecheterieDatabase.TableHabitat.NUM_CP));
                h.setVille(c.getString(DecheterieDatabase.TableHabitat.NUM_VILLE));
                h.setNbLgt(c.getInt(DecheterieDatabase.TableHabitat.NUM_NB_LGT));
                h.setNbHabitant(c.getInt(DecheterieDatabase.TableHabitat.NUM_NB_HABITANT));
                h.setNom(c.getString(DecheterieDatabase.TableHabitat.NUM_NOM));
                h.setReference(c.getString(DecheterieDatabase.TableHabitat.NUM_REFERENCE));
                h.setCoordonneesX(c.getString(DecheterieDatabase.TableHabitat.NUM_COORDONNEES_X));
                h.setCoordonneesY(c.getString(DecheterieDatabase.TableHabitat.NUM_COORDONNEES_Y));
                h.setComplement(c.getString(DecheterieDatabase.TableHabitat.NUM_COMPLEMENT));
                h.setDernierMaj(c.getString(DecheterieDatabase.TableHabitat.NUM_DERNIER_MAJ));
                h.setNumero(c.getString(DecheterieDatabase.TableHabitat.NUM_NUMERO));
                h.setActif((c.getInt(DecheterieDatabase.TableHabitat.NUM_IS_ACTIF) == 1) ? true : false);
                h.setActivites(c.getString(DecheterieDatabase.TableHabitat.NUM_ACTIVITES));
                h.setAdresse2(c.getString(DecheterieDatabase.TableHabitat.NUM_ADRESSE_2));
                h.setRemarque(c.getString(DecheterieDatabase.TableHabitat.NUM_REMARQUE));
                h.setIdTypeHabitat(c.getInt(DecheterieDatabase.TableHabitat.NUM_ID_TYPE_HABITAT));
                h.setIdAccount(c.getInt(DecheterieDatabase.TableHabitat.NUM_ID_ACCOUNT));
                return h;
            }
            else{
                return null;
            }
        }catch(Exception e){
            return h;
        }


    }

    private ArrayList<Habitat> cursorToListeHabitat(Cursor c) {
        ArrayList<Habitat> habitatList = new ArrayList<>();
        try {
            if (c.moveToFirst()) {
                do {
                    Habitat h = new Habitat();
                    h.setIdHabitat(c.getInt(DecheterieDatabase.TableHabitat.NUM_ID_HABITAT));
                    h.setAdresse(c.getString(DecheterieDatabase.TableHabitat.NUM_ADRESSE));
                    h.setCp(c.getString(DecheterieDatabase.TableHabitat.NUM_CP));
                    h.setVille(c.getString(DecheterieDatabase.TableHabitat.NUM_VILLE));
                    h.setNbLgt(c.getInt(DecheterieDatabase.TableHabitat.NUM_NB_LGT));
                    h.setNbHabitant(c.getInt(DecheterieDatabase.TableHabitat.NUM_NB_HABITANT));
                    h.setNom(c.getString(DecheterieDatabase.TableHabitat.NUM_NOM));
                    h.setReference(c.getString(DecheterieDatabase.TableHabitat.NUM_REFERENCE));
                    h.setCoordonneesX(c.getString(DecheterieDatabase.TableHabitat.NUM_COORDONNEES_X));
                    h.setCoordonneesY(c.getString(DecheterieDatabase.TableHabitat.NUM_COORDONNEES_Y));
                    h.setComplement(c.getString(DecheterieDatabase.TableHabitat.NUM_COMPLEMENT));
                    h.setDernierMaj(c.getString(DecheterieDatabase.TableHabitat.NUM_DERNIER_MAJ));
                    h.setNumero(c.getString(DecheterieDatabase.TableHabitat.NUM_NUMERO));
                    h.setActif((c.getInt(DecheterieDatabase.TableHabitat.NUM_IS_ACTIF) == 1) ? true : false);
                    h.setActivites(c.getString(DecheterieDatabase.TableHabitat.NUM_ACTIVITES));
                    h.setAdresse2(c.getString(DecheterieDatabase.TableHabitat.NUM_ADRESSE_2));
                    h.setRemarque(c.getString(DecheterieDatabase.TableHabitat.NUM_REMARQUE));
                    h.setIdTypeHabitat(c.getInt(DecheterieDatabase.TableHabitat.NUM_ID_TYPE_HABITAT));
                    h.setIdAccount(c.getInt(DecheterieDatabase.TableHabitat.NUM_ID_ACCOUNT));
                    habitatList.add(h);
                } while (c.moveToNext());

                c.close();
            }
            return habitatList;
        }catch(Exception e){
            return habitatList;
        }
    }

    /*
    Vider la table
     */

    public void clearHabitat() {
        db.execSQL("delete from " + DecheterieDatabase.TableHabitat.TABLE_NAME);
    }
}
