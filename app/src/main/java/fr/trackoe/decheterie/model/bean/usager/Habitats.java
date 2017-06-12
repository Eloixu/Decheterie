package fr.trackoe.decheterie.model.bean.usager;

import java.util.ArrayList;

import fr.trackoe.decheterie.model.bean.global.ContenantBean;
import fr.trackoe.decheterie.model.bean.usager.Habitat;

/**
 * Created by Remi on 02/05/2017.
 */
public class Habitats extends ContenantBean {
    private ArrayList<Habitat> listHabitat;

    public Habitats() {
        this.listHabitat = new ArrayList<>();
    }

    public ArrayList<Habitat> getListHabitat() {
        return listHabitat;
    }

    public void setListHabitat(ArrayList<Habitat> listHabitat) {
        this.listHabitat = listHabitat;
    }

    public void addHabitat(int idHabitat, String adresse, String cp, String ville, int nbLgt, int nbHabitant, String reference, String coordonneesX, String coordonneesY, String complement, String dernierMaj, String numero, boolean isActif, String adresse2, String remarque, int idTypeHabitat, int idAccount, String dateDebut, String dateFin) {
        this.listHabitat.add(new Habitat(idHabitat, adresse, cp, ville, nbLgt, nbHabitant, reference, coordonneesX, coordonneesY, complement, dernierMaj, numero, isActif, adresse2, remarque, idTypeHabitat, idAccount, dateDebut, dateFin));
    }
}
