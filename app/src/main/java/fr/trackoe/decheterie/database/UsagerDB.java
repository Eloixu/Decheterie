package fr.trackoe.decheterie.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

import fr.trackoe.decheterie.Utils;
import fr.trackoe.decheterie.model.bean.global.Module;
import fr.trackoe.decheterie.model.bean.global.Modules;
import fr.trackoe.decheterie.model.bean.usager.Usager;
import fr.trackoe.decheterie.model.bean.usager.UsagerFilter;

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
        values.put(DecheterieDatabase.TableUsager.PRENOM, usager.getPrenom());
        values.put(DecheterieDatabase.TableUsager.EMAIL, usager.getEmail());
        values.put(DecheterieDatabase.TableUsager.CIVILITE, usager.getCivilite());
        values.put(DecheterieDatabase.TableUsager.REFERENCE, usager.getReference());
        values.put(DecheterieDatabase.TableUsager.RAISON_SOCIALE, usager.getRaisonSociale());
        values.put(DecheterieDatabase.TableUsager.ACTIVITE, usager.getActivite());
        values.put(DecheterieDatabase.TableUsager.TELEPHONE1, usager.getTelephone1());
        values.put(DecheterieDatabase.TableUsager.TELEPHONE2, usager.getTelephone2());
        values.put(DecheterieDatabase.TableUsager.PASSWORD, usager.getPassword());
        values.put(DecheterieDatabase.TableUsager.COMMENTAIRE, usager.getCommentaire());
        values.put(DecheterieDatabase.TableUsager.IS_ACTIF, usager.isActif() ? 1 : 0);
        values.put(DecheterieDatabase.TableUsager.SIREN, usager.getSiren());
        values.put(DecheterieDatabase.TableUsager.SIRET, usager.getSiret());
        values.put(DecheterieDatabase.TableUsager.CODE_APE, usager.getCodeApe());
        values.put(DecheterieDatabase.TableUsager.SOUMIS_RS, usager.getSoumisRS());

        return db.insertOrThrow(DecheterieDatabase.TableUsager.TABLE_NAME, null, values);
    }

    public void updateUsager(Usager usager) {
        ContentValues values = new ContentValues();
        values.put(DecheterieDatabase.TableUsager.ID_USAGER, usager.getId());
        values.put(DecheterieDatabase.TableUsager.ID_ACCOUNT, usager.getIdAccount());
        values.put(DecheterieDatabase.TableUsager.NOM, usager.getNom());
        values.put(DecheterieDatabase.TableUsager.DATE_MAJ, usager.getDateMaj());
        values.put(DecheterieDatabase.TableUsager.PRENOM, usager.getPrenom());
        values.put(DecheterieDatabase.TableUsager.EMAIL, usager.getEmail());
        values.put(DecheterieDatabase.TableUsager.CIVILITE, usager.getCivilite());
        values.put(DecheterieDatabase.TableUsager.REFERENCE, usager.getReference());
        values.put(DecheterieDatabase.TableUsager.RAISON_SOCIALE, usager.getRaisonSociale());
        values.put(DecheterieDatabase.TableUsager.ACTIVITE, usager.getActivite());
        values.put(DecheterieDatabase.TableUsager.TELEPHONE1, usager.getTelephone1());
        values.put(DecheterieDatabase.TableUsager.TELEPHONE2, usager.getTelephone2());
        values.put(DecheterieDatabase.TableUsager.PASSWORD, usager.getPassword());
        values.put(DecheterieDatabase.TableUsager.COMMENTAIRE, usager.getCommentaire());
        values.put(DecheterieDatabase.TableUsager.IS_ACTIF, usager.isActif() ? 1 : 0);
        values.put(DecheterieDatabase.TableUsager.SIREN, usager.getSiren());
        values.put(DecheterieDatabase.TableUsager.SIRET, usager.getSiret());
        values.put(DecheterieDatabase.TableUsager.CODE_APE, usager.getCodeApe());
        values.put(DecheterieDatabase.TableUsager.SOUMIS_RS, usager.getSoumisRS());

        db.update(DecheterieDatabase.TableUsager.TABLE_NAME, values,DecheterieDatabase.TableUsager.ID_USAGER + "=" + usager.getId(),null);
    }

    public void deleteUsagerByIdentifiant(int id){
        db.execSQL("delete from " + DecheterieDatabase.TableUsager.TABLE_NAME + " WHERE " + DecheterieDatabase.TableUsager.ID_USAGER + "=" + id);
    }

    public Usager getUsagerFromID(int id) {
        String query = "SELECT * FROM " + DecheterieDatabase.TableUsager.TABLE_NAME + " WHERE " + DecheterieDatabase.TableUsager.ID_USAGER + " = " + id;
        Cursor cursor = db.rawQuery(query, null);
        return cursorToUsager(cursor);
    }

    public ArrayList<Usager> getAllUsager() {
        ArrayList<Usager> usagerList;
        String query = "SELECT * FROM " + DecheterieDatabase.TableUsager.TABLE_NAME  + ";";
        Cursor cursor = db.rawQuery(query, null);
        usagerList = cursorToListeUsager(cursor);
        return usagerList;
    }

    public ArrayList<Usager> getUsagerListByName(String nom) {
        ArrayList<Usager> usagerList;
        String query = "SELECT * FROM " + DecheterieDatabase.TableUsager.TABLE_NAME + " WHERE " + DecheterieDatabase.TableUsager.NOM + " LIKE " + "'%" + nom + "%'" +  ";";
        Cursor cursor = db.rawQuery(query, null);
        usagerList = cursorToListeUsager(cursor);
        return usagerList;
    }

    public Usager cursorToUsager(Cursor c){
        Usager u = new Usager();
        try {
            if (c.moveToFirst()) {
                u.setId(c.getInt(DecheterieDatabase.TableUsager.NUM_ID_USAGER));
                u.setIdAccount(c.getInt(DecheterieDatabase.TableUsager.NUM_ID_ACCOUNT));
                u.setNom(c.getString(DecheterieDatabase.TableUsager.NUM_NOM));
                u.setDateMaj(c.getString(DecheterieDatabase.TableUsager.NUM_DATE_MAJ));
                u.setPrenom(c.getString(DecheterieDatabase.TableUsager.NUM_PRENOM));
                u.setEmail(c.getString(DecheterieDatabase.TableUsager.NUM_EMAIL));
                u.setCivilite(c.getString(DecheterieDatabase.TableUsager.NUM_CIVILITE));
                u.setReference(c.getString(DecheterieDatabase.TableUsager.NUM_REFERENCE));
                u.setRaisonSociale(c.getString(DecheterieDatabase.TableUsager.NUM_RAISON_SOCIALE));
                u.setActivite(c.getString(DecheterieDatabase.TableUsager.NUM_ACTIVITE));
                u.setTelephone1(c.getString(DecheterieDatabase.TableUsager.NUM_TELEPHONE1));
                u.setTelephone2(c.getString(DecheterieDatabase.TableUsager.NUM_TELEPHONE2));
                u.setPassword(c.getString(DecheterieDatabase.TableUsager.NUM_PASSWORD));
                u.setCommentaire(c.getString(DecheterieDatabase.TableUsager.NUM_COMMENTAIRE));
                u.setActif(c.getInt(DecheterieDatabase.TableUsager.NUM_IS_ACTIF) == 1 ? true : false);
                u.setSiren(c.getString(DecheterieDatabase.TableUsager.NUM_SIREN));
                u.setSiret(c.getString(DecheterieDatabase.TableUsager.NUM_SIRET));
                u.setCodeApe(c.getString(DecheterieDatabase.TableUsager.NUM_CODE_APE));
                u.setSoumisRS(c.getString(DecheterieDatabase.TableUsager.NUM_SOUMIS_RS));
                c.close();
                return u;
            }else{
                c.close();
                return null;
            }
        }
        catch(Exception e){
            return u;
        }

    }

    private ArrayList<Usager> cursorToListeUsager(Cursor c) {
        ArrayList<Usager> usagerList = new ArrayList<>();
        try {
            if (c.moveToFirst()) {
                do {
                    Usager u = new Usager();
                    u.setId(c.getInt(DecheterieDatabase.TableUsager.NUM_ID_USAGER));
                    u.setIdAccount(c.getInt(DecheterieDatabase.TableUsager.NUM_ID_ACCOUNT));
                    u.setNom(c.getString(DecheterieDatabase.TableUsager.NUM_NOM));
                    u.setDateMaj(c.getString(DecheterieDatabase.TableUsager.NUM_DATE_MAJ));
                    u.setPrenom(c.getString(DecheterieDatabase.TableUsager.NUM_PRENOM));
                    u.setEmail(c.getString(DecheterieDatabase.TableUsager.NUM_EMAIL));
                    u.setCivilite(c.getString(DecheterieDatabase.TableUsager.NUM_CIVILITE));
                    u.setReference(c.getString(DecheterieDatabase.TableUsager.NUM_REFERENCE));
                    u.setRaisonSociale(c.getString(DecheterieDatabase.TableUsager.NUM_RAISON_SOCIALE));
                    u.setActivite(c.getString(DecheterieDatabase.TableUsager.NUM_ACTIVITE));
                    u.setTelephone1(c.getString(DecheterieDatabase.TableUsager.NUM_TELEPHONE1));
                    u.setTelephone2(c.getString(DecheterieDatabase.TableUsager.NUM_TELEPHONE2));
                    u.setPassword(c.getString(DecheterieDatabase.TableUsager.NUM_PASSWORD));
                    u.setCommentaire(c.getString(DecheterieDatabase.TableUsager.NUM_COMMENTAIRE));
                    u.setActif(c.getInt(DecheterieDatabase.TableUsager.NUM_IS_ACTIF) == 1 ? true : false);
                    u.setSiren(c.getString(DecheterieDatabase.TableUsager.NUM_SIREN));
                    u.setSiret(c.getString(DecheterieDatabase.TableUsager.NUM_SIRET));
                    u.setCodeApe(c.getString(DecheterieDatabase.TableUsager.NUM_CODE_APE));
                    u.setSoumisRS(c.getString(DecheterieDatabase.TableUsager.NUM_SOUMIS_RS));
                    usagerList.add(u);
                } while (c.moveToNext());

                c.close();
            }
            return usagerList;
        }
        catch(Exception e){
            return usagerList;
        }
    }

    /*
    Vider la table
     */

    public void clearUsager() {
        db.execSQL("delete from " + DecheterieDatabase.TableUsager.TABLE_NAME);
    }

    // TODO Réécrire requête
    public ArrayList<UsagerFilter> filterResult(String nomUsager, int idTypeCarte, String adresse) {
        ArrayList<UsagerFilter> results;
        String query = "SELECT usa.id_usager, usa.nom, c.dch_type_carte_id, hab.numero as h1_numero, hab.complement as h1_complement, hab.adresse as h1_adresse, hab.cp as h1_cp, hab.ville as h1_ville, hab1.numero as h2_numero, hab1.complement as h2_complement, hab1.adresse as h2_adresse, hab1.cp as h2_cp, hab1.ville as h2_ville FROM usager AS usa " +
                "LEFT JOIN dch_compte_prepaye AS cp ON usa.id_usager = cp.dch_usager_id LEFT JOIN dch_carte_active AS ca ON cp.id = ca.dch_compte_prepaye_id LEFT JOIN dch_carte AS c ON ca.dch_carte_id = c.id " +
                "LEFT JOIN usager_habitat AS guh ON usa.id_usager = guh.id_usager LEFT JOIN habitat AS hab ON  guh.id_habitat = hab.id_habitat " +
                "LEFT JOIN usager_menage AS gum ON usa.id_usager = gum.id_usager LEFT JOIN menage AS mena ON gum.id_menage = mena.id_menage LEFT JOIN local AS loc ON mena.local_id = loc.id_local LEFT JOIN habitat AS hab1 ON loc.habitat_id = hab1.id_habitat " +
                "WHERE usa.is_actif AND ca.is_active = 1 AND (hab.is_actif = 1 OR hab1.is_actif = 1) AND usa.nom LIKE '%" + nomUsager + "%' AND c.dch_type_carte_id = " + idTypeCarte;

        if(!Utils.isStringEmpty(adresse) && adresse.length() > 0) {
            query += " AND ";

            String ad[] = adresse.split(" ");
            for (int i = 0; i < ad.length; i++) {
                if(i >0) {
                    query += " AND ";
                }
                query += "((hab.numero LIKE '%" + ad[i] + "%' OR hab1.numero LIKE '%" + ad[i] + "%') OR " +
                        "(hab.complement LIKE '%" + ad[i] + "%' OR hab1.complement LIKE '%" + ad[i] + "%') OR " +
                        "(hab.adresse LIKE '%" + ad[i] + "%' OR hab1.adresse LIKE '%" + ad[i] + "%') OR " +
                        "(hab.cp LIKE '%" + ad[i] + "%' OR hab1.cp LIKE '%" + ad[i] + "%') OR " +
                        "(hab.ville LIKE '%" + ad[i] + "%' OR hab1.ville LIKE '%" + ad[i] + "%'))";
            }

            query += " ";
        }

        Cursor cursor = db.rawQuery(query, null);
        results = cursorToListeFilterUsager(cursor);
        return results;
    }

    private ArrayList<UsagerFilter> cursorToListeFilterUsager(Cursor c) {
        ArrayList<UsagerFilter> ul = new ArrayList<>();
        try {
            if (c.moveToFirst()) {
                do {
                    UsagerFilter u = new UsagerFilter();

                    u.setId(c.getInt(0));
                    u.setNom(c.getString(1));
                    u.setIdTypeCarte(c.getInt(2));

                    u.setH1Numero(c.getString(3));
                    u.setH1Complement(c.getString(4));
                    u.setH1Adresse(c.getString(5));
                    u.setH1Cp(c.getString(6));
                    u.setH1Ville(c.getString(7));

                    u.setH2Numero(c.getString(8));
                    u.setH2Complement(c.getString(9));
                    u.setH2Adresse(c.getString(10));
                    u.setH2Cp(c.getString(11));
                    u.setH2Ville(c.getString(12));

                    ul.add(u);
                } while (c.moveToNext());

                c.close();
            }
            return ul;
        }
        catch(Exception e){
            return ul;
        }
    }

}
