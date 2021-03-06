package fr.trackoe.decheterie.model.bean.usager;

/**
 * Created by Haocheng on 07/04/2017.
 */

public class Habitat {
    private int idHabitat;
    private String adresse;
    private String cp;
    private String ville;
    private int nbLgt;
    private int nbHabitant;
    private String reference;
    private String coordonneesX;
    private String coordonneesY;
    private String complement;
    private String dernierMaj;
    private String numero;
    private boolean isActif;
    private String adresse2;
    private String remarque;
    private int idTypeHabitat;
    private int idAccount;
    private String dateDebut;
    private String dateFin;

    public Habitat() {

    }

    public Habitat(int idHabitat, String adresse, String cp, String ville, int nbLgt, int nbHabitant, String reference, String coordonneesX, String coordonneesY, String complement, String dernierMaj, String numero, boolean isActif, String adresse2, String remarque, int idTypeHabitat, int idAccount, String dateDebut, String dateFin) {
        this.idHabitat = idHabitat;
        this.adresse = adresse;
        this.cp = cp;
        this.ville = ville;
        this.nbLgt = nbLgt;
        this.nbHabitant = nbHabitant;
        this.reference = reference;
        this.coordonneesX = coordonneesX;
        this.coordonneesY = coordonneesY;
        this.complement = complement;
        this.dernierMaj = dernierMaj;
        this.numero = numero;
        this.isActif = isActif;
        this.adresse2 = adresse2;
        this.remarque = remarque;
        this.idTypeHabitat = idTypeHabitat;
        this.idAccount = idAccount;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public String getCoordonneesY() {
        return coordonneesY;
    }

    public void setCoordonneesY(String coordonneesY) {
        this.coordonneesY = coordonneesY;
    }

    public int getIdHabitat() {
        return idHabitat;
    }

    public void setIdHabitat(int idHabitat) {
        this.idHabitat = idHabitat;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public int getNbLgt() {
        return nbLgt;
    }

    public void setNbLgt(int nbLgt) {
        this.nbLgt = nbLgt;
    }

    public int getNbHabitant() {
        return nbHabitant;
    }

    public void setNbHabitant(int nbHabitant) {
        this.nbHabitant = nbHabitant;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getCoordonneesX() {
        return coordonneesX;
    }

    public void setCoordonneesX(String coordonneesX) {
        this.coordonneesX = coordonneesX;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getDernierMaj() {
        return dernierMaj;
    }

    public void setDernierMaj(String dernierMaj) {
        this.dernierMaj = dernierMaj;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public boolean isActif() {
        return isActif;
    }

    public void setActif(boolean actif) {
        isActif = actif;
    }

    public String getAdresse2() {
        return adresse2;
    }

    public void setAdresse2(String adresse2) {
        this.adresse2 = adresse2;
    }

    public String getRemarque() {
        return remarque;
    }

    public void setRemarque(String remarque) {
        this.remarque = remarque;
    }

    public int getIdTypeHabitat() {
        return idTypeHabitat;
    }

    public void setIdTypeHabitat(int idTypeHabitat) {
        this.idTypeHabitat = idTypeHabitat;
    }

    public int getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(int idAccount) {
        this.idAccount = idAccount;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }
}
