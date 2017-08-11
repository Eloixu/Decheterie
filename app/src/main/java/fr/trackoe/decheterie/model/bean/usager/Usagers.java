package fr.trackoe.decheterie.model.bean.usager;

import java.util.ArrayList;

import fr.trackoe.decheterie.model.bean.global.ContenantBean;
import fr.trackoe.decheterie.model.bean.usager.Menage;
import fr.trackoe.decheterie.model.bean.usager.Usager;

/**
 * Created by Remi on 04/05/2017.
 */
public class Usagers extends ContenantBean {
    private ArrayList<Usager> listUsager;

    public Usagers() {
        this.listUsager = new ArrayList<>();
    }

    public ArrayList<Usager> getListUsager() {
        return listUsager;
    }

    public void setListUsager(ArrayList<Usager> listUsager) {
        this.listUsager = listUsager;
    }

    public void addUsager(int id, int idAccount, String nom, String dateMaj, String prenom, String email, String civilite, String reference, String raisonSociale, String activite,
                          String telephone1, String telephone2, String password, String commentaire, boolean isActif, String siren, String siret, String codeApe, String soumisRS) {

        this.listUsager.add(new Usager(id, idAccount, nom, dateMaj, prenom, email, civilite, reference, raisonSociale, activite,
                telephone1, telephone2, password, commentaire, isActif, siren, siret, codeApe, soumisRS));
    }
}
