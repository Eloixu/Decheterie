package fr.trackoe.decheterie.model.bean.usager;

/**
 * Created by Remi on 05/04/2017.
 */
public class Usager {
    private int id;
    private int idAccount;
    private String nom;
    private String dateMaj;
    private String prenom;
    private String email;
    private String civilite;
    private String reference;
    private String raisonSociale;
    private String activite;
    private String telephone1;
    private String telephone2;
    private String password;
    private String commentaire;
    private boolean isActif;
    private String siren;
    private String siret;
    private String codeApe;
    private String soumisRS;

    public Usager() {

    }

    public Usager(int id, int idAccount, String nom, String dateMaj, String prenom, String email, String civilite, String reference, String raisonSociale, String activite,
                  String telephone1, String telephone2, String password, String commentaire, boolean isActif, String siren, String siret, String codeApe, String soumisRS) {
        this.id = id;
        this.idAccount = idAccount;
        this.nom = nom;
        this.dateMaj = dateMaj;
        this.prenom = prenom;
        this.email = email;
        this.civilite = civilite;
        this.reference = reference;
        this.raisonSociale = raisonSociale;
        this.activite = activite;
        this.telephone1 = telephone1;
        this.telephone2 = telephone2;
        this.password = password;
        this.commentaire = commentaire;
        this.isActif = isActif;
        this.siren = siren;
        this.siret = siret;
        this.codeApe = codeApe;
        this.soumisRS = soumisRS;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(int idAccount) {
        this.idAccount = idAccount;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDateMaj() {
        return dateMaj;
    }

    public void setDateMaj(String dateMaj) {
        this.dateMaj = dateMaj;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCivilite() {
        return civilite;
    }

    public void setCivilite(String civilite) {
        this.civilite = civilite;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getActivite() {
        return activite;
    }

    public void setActivite(String activite) {
        this.activite = activite;
    }

    public String getTelephone1() {
        return telephone1;
    }

    public void setTelephone1(String telephone1) {
        this.telephone1 = telephone1;
    }

    public String getTelephone2() {
        return telephone2;
    }

    public void setTelephone2(String telephone2) {
        this.telephone2 = telephone2;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public String getSiren() {
        return siren;
    }

    public void setSiren(String siren) {
        this.siren = siren;
    }

    public String getSiret() {
        return siret;
    }

    public void setSiret(String siret) {
        this.siret = siret;
    }

    public String getRaisonSociale() {
        return raisonSociale;
    }

    public void setRaisonSociale(String raisonSociale) {
        this.raisonSociale = raisonSociale;
    }

    public boolean isActif() {
        return isActif;
    }

    public void setActif(boolean actif) {
        isActif = actif;
    }

    public String getCodeApe() {
        return codeApe;
    }

    public void setCodeApe(String codeApe) {
        this.codeApe = codeApe;
    }

    public String getSoumisRS() {
        return soumisRS;
    }

    public void setSoumisRS(String soumisRS) {
        this.soumisRS = soumisRS;
    }
}
